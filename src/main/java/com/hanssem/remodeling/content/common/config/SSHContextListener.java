package com.hanssem.remodeling.content.common.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.springframework.context.annotation.Profile;

/*
 * 로컬에서 개발 디비 사용 시에만 사용
 * */
@Profile("local")
@WebListener
public class SSHContextListener implements ServletContextListener {

    private SSHConnectionConfig ssh;

    public SSHContextListener() {
        super();
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ssh = new SSHConnectionConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        ssh.closeSSH();
    }
}
