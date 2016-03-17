package tr.org.liderahenk.web.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tr.org.liderahenk.lider.core.api.log.IOperationLogService;
import tr.org.liderahenk.lider.core.api.persistence.enums.CrudType;

/**
 * Main filter class which is used to authenticate requests.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class LiderAuthenticationFilter extends AuthenticatingFilter {

	private static Logger logger = LoggerFactory.getLogger(LiderAuthenticationFilter.class);

	@Autowired
	private IOperationLogService logService;

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		logger.debug("parsing token from request header");
		HttpServletRequest req = (HttpServletRequest) request;

		String user = req.getHeader("username");
		String pwd = req.getHeader("password");

		logger.debug("creating usernamepassword token for user: {}, pwd: {}", user, "*****");
		return new UsernamePasswordToken(user, pwd);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		logger.debug("will try to authenticate now...");
		return executeLogin(request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String user = req.getHeader("username");
		try {
			logService.saveLog(user, CrudType.LOGIN, "Unauthorized access request", null, getHost(request));
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
		}
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}

}
