package org.springframework.security.web.authentication;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.filter.state.DefaultStateKeyGenerator;
import org.springframework.security.oauth2.client.filter.state.StateKeyGenerator;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.auth.DefaultClientAuthenticationHandler;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriTemplate;

public class SdAuthorizationCodeAccessTokenProvider extends OAuth2AccessTokenSupport implements AccessTokenProvider {

	private Map<String, String> methodMap = new HashMap<String, String>();

	private ClientAuthenticationHandler authenticationHandler = new DefaultClientAuthenticationHandler();

	protected HttpMethod getHttpMethod(OAuth2ProtectedResourceDetails resource) {

		if ("GET".equals(methodMap.get(resource.getId()))) {
			return HttpMethod.GET;
		} else {
			return HttpMethod.POST;
		}
	}

	protected OAuth2AccessToken retrieveToken(MultiValueMap<String, String> form, OAuth2ProtectedResourceDetails resource) {

		try {
			// Prepare headers and form before going into rest template call in
			// case the URI is affected by the result
			HttpHeaders headers = new HttpHeaders();
			authenticationHandler.authenticateTokenRequest(resource, form, headers);
			return getRestTemplate().execute(getAccessTokenUri(resource, form), getHttpMethod(resource), getRequestCallback(resource, form, headers), getResponseExtractor(), form.toSingleValueMap());
		} catch (OAuth2Exception oe) {
			throw new OAuth2AccessDeniedException("Access token denied.", resource, oe);
		} catch (RestClientException rce) {
			throw new OAuth2AccessDeniedException("Error requesting access token.", resource, rce);
		}
	}

	@Override
	protected String getAccessTokenUri(OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form) {

		String accessTokenUri = resource.getAccessTokenUri();

		if (logger.isDebugEnabled()) {
			logger.debug("Retrieving token from " + accessTokenUri);
		}

		StringBuilder builder = new StringBuilder(accessTokenUri);

		if (getHttpMethod(resource) == HttpMethod.GET) {
			String separator = "?";
			if (accessTokenUri.contains("?")) {
				separator = "&";
			}

			for (String key : form.keySet()) {
				builder.append(separator);
				builder.append(key + "={" + key + "}");
				separator = "&";
			}
		}

		return builder.toString();
	}

	public void setMethodMap(Map<String, String> methodMap) {
		this.methodMap = methodMap;
	}

	// /////////////////// copy AuthorizationCodeAccessTokenProvider //////////

	private StateKeyGenerator stateKeyGenerator = new DefaultStateKeyGenerator();

	/**
	 * @param stateKeyGenerator
	 *            the stateKeyGenerator to set
	 */
	public void setStateKeyGenerator(StateKeyGenerator stateKeyGenerator) {
		this.stateKeyGenerator = stateKeyGenerator;
	}

	public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
		return resource instanceof AuthorizationCodeResourceDetails && "authorization_code".equals(resource.getGrantType());
	}

	public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
		return supportsResource(resource);
	}

	public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, AccessDeniedException {

		AuthorizationCodeResourceDetails resource = (AuthorizationCodeResourceDetails) details;

		if (request.getAuthorizationCode() == null) {
			throw getRedirectForAuthorization(resource, request);
		} else {
			return retrieveToken(getParametersForTokenRequest(resource, request), resource);
		}

	}

	public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource, OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("grant_type", "refresh_token");
		form.add("refresh_token", refreshToken.getValue());
		try {
			return retrieveToken(form, resource);
		} catch (OAuth2AccessDeniedException e) {
			throw getRedirectForAuthorization((AuthorizationCodeResourceDetails) resource, request);
		}
	}

	private MultiValueMap<String, String> getParametersForTokenRequest(AuthorizationCodeResourceDetails resource, AccessTokenRequest request) {

		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.add("grant_type", "authorization_code");
		form.add("code", request.getAuthorizationCode());

		String redirectUri = resource.getPreEstablishedRedirectUri();
		if (redirectUri == null && request.getPreservedState() != null) {
			// no pre-established redirect uri: use the preserved state
			// TODO: treat redirect URI as a special kind of state (this is a
			// historical mini hack)
			redirectUri = String.valueOf(request.getPreservedState());
		}
		if (request.getStateKey() != null) {
			form.add("state", request.getStateKey());
		}

		if (redirectUri != null) {
			form.add("redirect_uri", redirectUri);
		}

		return form;

	}

	protected UserRedirectRequiredException getRedirectForAuthorization(AuthorizationCodeResourceDetails resource, AccessTokenRequest request) {

		// we don't have an authorization code yet. So first get that.
		TreeMap<String, String> requestParameters = new TreeMap<String, String>();
		requestParameters.put("response_type", "code"); // oauth2 spec, section
														// 3
		requestParameters.put("client_id", resource.getClientId());
		// Client secret is not required in the initial authorization request

		String redirectUri = resource.getPreEstablishedRedirectUri();
		String userRedirectUri = request.getCurrentUri();
		if (redirectUri == null) {

			redirectUri = userRedirectUri;
			if (redirectUri == null) {
				throw new IllegalStateException("No redirect URI has been established for the current request.");
			}
			requestParameters.put("redirect_uri", redirectUri);

		} else {

			redirectUri = null;

		}

		if (resource.isScoped()) {

			StringBuilder builder = new StringBuilder();
			List<String> scope = resource.getScope();

			if (scope != null) {
				Iterator<String> scopeIt = scope.iterator();
				while (scopeIt.hasNext()) {
					builder.append(scopeIt.next());
					if (scopeIt.hasNext()) {
						builder.append(' ');
					}
				}
			}

			requestParameters.put("scope", builder.toString());
		}

		String authUri = resource.getUserAuthorizationUri();
		UriTemplate uriTemplate = new UriTemplate(authUri);
		List<String> variableNames = uriTemplate.getVariableNames();
		Map<String, String> params = new HashMap<String, String>(variableNames.size());
		for (String var : variableNames) {
			List<String> vals = request.get(var);
			if (vals != null && !vals.isEmpty())
				params.put(var, vals.get(0));
			else
				params.put(var, "");
		}
		URI expanded = uriTemplate.expand(params);

		UserRedirectRequiredException redirectException = new UserRedirectRequiredException(expanded.toString(), requestParameters);

		String stateKey = stateKeyGenerator.generateKey(resource);
		if (stateKey != null) {
			redirectException.setStateKey(stateKey);
			if (userRedirectUri != null) {
				redirectException.setStateToPreserve(userRedirectUri);
			}
		}

		return redirectException;

	}

}
