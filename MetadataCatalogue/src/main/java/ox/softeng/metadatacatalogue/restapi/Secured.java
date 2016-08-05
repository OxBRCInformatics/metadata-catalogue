package ox.softeng.metadatacatalogue.restapi;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.ws.rs.NameBinding;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
@NameBinding
public @interface Secured { 
	
	boolean allowUnAuthenticated() default false;
	
}