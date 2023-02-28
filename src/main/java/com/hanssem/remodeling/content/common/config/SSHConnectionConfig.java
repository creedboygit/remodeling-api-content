package com.hanssem.remodeling.content.common.config;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SSHConnectionConfig {

    private static final String SSH_REMOTE_SERVER = "3.35.176.81"; // 우회용 프록시 서버 ip 넣기
    private static final String SSH_REMOTE_USERNAME = "ec2-user"; // 우회용 프록시 서버 username 넣기
    private static final String PEM_PATH =
            System.getProperty("user.home") + "/.ssh/HANSSEM-DT-REMODELING.pem"; // pem 키 path 넣기

    private static final String MYSQL_REMOTE_SERVER = "dev-remodeling-database-1.cluster-cg6h87hioico.ap-northeast-2.rds.amazonaws.com"; // rds endpoint 넣기
    private static final int MYSQL_PORT = 3306;
    private static final String REDIS_REMOTE_SERVER = "dev-remodeling-cache.y9b7eg.ng.0001.apn2.cache.amazonaws.com"; // rds endpoint 넣기
    private static final int REDIS_PORT = 6379;

    private Session session;

    public void closeSSH() {
        if (Objects.nonNull(session) && session.isConnected()) {
            session.disconnect();
        }
    }

    public SSHConnectionConfig() {
        JSch jsch = new JSch();
        try {
            jsch.addIdentity(PEM_PATH);
            session = jsch.getSession(SSH_REMOTE_USERNAME, SSH_REMOTE_SERVER, 22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setServerAliveInterval(120 * 1000);
            session.setServerAliveCountMax(1000);
            session.connect();
            session.setPortForwardingL(MYSQL_PORT, MYSQL_REMOTE_SERVER, MYSQL_PORT);
            session.setPortForwardingL(REDIS_PORT, REDIS_REMOTE_SERVER, REDIS_PORT);
        } catch (Exception e) {
            log.error("Error -> {}", e.getMessage());
            e.printStackTrace();
        }
    }
}

