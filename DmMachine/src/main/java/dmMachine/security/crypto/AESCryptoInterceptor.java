package dmMachine.security.crypto;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AESCryptoInterceptor implements HandlerInterceptor {
	

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    	String encryptedData = getData(request);
//        String decryptedData = encryptionService.decrypt(encryptedData);
//        request.setAttribute("data", decryptedData);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        String responseData = (String) request.getAttribute("data");
//        String encryptedResponse = encryptionService.encrypt(responseData);
//        response.getWriter().write(encryptedResponse);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        
    }

}
