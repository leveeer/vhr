package edu.jxau.vhr.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import edu.jxau.vhr.annotation.OpLog;
import edu.jxau.vhr.model.Oplog;
import edu.jxau.vhr.service.OplogService;
import edu.jxau.vhr.utils.HttpContextUtils;
import edu.jxau.vhr.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class OpLogAspect {

    @Autowired
    private OplogService oplogService;

    @Pointcut("@annotation(edu.jxau.vhr.annotation.OpLog)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        // 执行时长(毫秒)
        long time = (System.currentTimeMillis() - beginTime);
        // 保存日志
        saveLog(point, time);
        return result;

    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Oplog oplog = new Oplog();
        OpLog logAnnotation = method.getAnnotation(OpLog.class);
        if (logAnnotation != null) {
            // 注解上的描述
            oplog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        oplog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += ", " + paramNames[i] + ":" + args[i];
            }
            oplog.setParams(params);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        oplog.setIp(IpUtils.getIpAddr(request));
        // 获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            oplog.setUsername(authentication.getName());
        }
        oplog.setExecutionTime((int) time);
        oplog.setVisitTime(new Date());
        // 保存系统日志
        oplogService.insertLog(oplog);
    }

}
