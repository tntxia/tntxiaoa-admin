package com.tntxia.oa.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录过滤
 * 
 * @author tntxia
 * @date 2015-2-24
 */
@WebFilter("/*")
public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		// 不过滤的uri
		String[] notFilter = new String[] { "login.do", "login.ftl","login.mvc",".css",
				".js", ".gif", ".png", ".htm", ".asp", ".jpg",".map" };

		HttpServletRequest req = (HttpServletRequest) request;

		// 请求的url
		String url = req.getRequestURL().toString();

		// 是否过滤
		boolean doFilter = true;
		for (String s : notFilter) {
			if (url.toLowerCase().endsWith(s.toLowerCase())) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}
		
		if (doFilter) {
			
			HttpSession session = req.getSession();
			
			// 执行过滤
			// 从session中获取登录者实体
			String username = (String) session.getAttribute("username");
			if (null == username) {
				String loginPath = "/login.mvc";
				request.getRequestDispatcher(loginPath).forward(request,
						response);
				return;
			}

			// 如果session中存在登录者和应用，则继续
			filterChain.doFilter(request, response);

		} else {
			// 如果不执行过滤，则继续
			filterChain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}