package starter.user.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView errorView(HttpServletRequest request,Exception e)throws Exception{
		ModelAndView mv=new ModelAndView();
		mv.addObject("exception", e.getMessage());
		mv.addObject("url", request.getRequestURL());
		mv.setViewName("error.html");
		return mv;
	}
}
