package rest.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
			throw new Exception("Korisnik nije ulogovan kao farmaceut.");
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsDermatolog)")
	public Object dermatologBofore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.DERMATOLOG)) {
			throw new Exception("Korisnik nije ulogovan kao dermatolog.");
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsAdminSistema)")
	public Object adminSistemaBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.ADMIN_SISTEMA)) {
			throw new Exception("Korisnik nije ulogovan kao admin sistema.");
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsAdminApoteke)")
	public Object adminApotekeBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.ADMIN_APOTEKE)) {
			throw new Exception("Korisnik nije ulogovan kao admin apoteke.");
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsPacijent)")
	public Object pacijentBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.PACIJENT)) {
			throw new Exception("Korisnik nije ulogovan kao pacijent.");
		}
		return joinPoint.proceed();
	}
	
	@Around("@annotation(AsDobavljac)")
	public Object dobavljacBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		KorisnikDTO currentUser = (KorisnikDTO) attr.getRequest().getSession().getAttribute("user");
		
		if(currentUser == null || !currentUser.getZaposlenjeKorisnika().equals(ZaposlenjeKorisnika.DOBAVLJAC)) {
			throw new Exception("Korisnik nije ulogovan kao dobavljac.");
		}
		return joinPoint.proceed();
	}
	
}
