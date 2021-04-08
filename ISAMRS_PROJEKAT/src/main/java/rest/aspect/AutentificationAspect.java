package rest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import rest.domain.ZaposlenjeKorisnika;
import rest.dto.KorisnikDTO;

@Component
@Aspect
public class AutentificationAspect {
	
	@Around("@annotation(AsFarmaceut)")
	public Object farmaceutBofore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.FARMACEUT)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsDermatolog)")
	public Object dermatologBofore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.DERMATOLOG)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsAdminSistema)")
	public Object adminSistemaBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.ADMIN_SISTEMA)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsAdminApoteke)")
	public Object adminApotekeBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.ADMIN_APOTEKE)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsPacijent)")
	public Object pacijentBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.PACIJENT)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsDobavljac)")
	public Object dobavljacBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.DOBAVLJAC)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		return joinPoint.proceed();
	}
	
}
