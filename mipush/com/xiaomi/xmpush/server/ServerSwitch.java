// 
// Decompiled by Procyon v0.5.36
// 

package com.xiaomi.xmpush.server;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

public class ServerSwitch
{
    private static final long REFRESH_SERVER_HOST_INTERVAL = 300000L;
    private static Server defaultServer;
    private static ServerSwitch INSTANCE;
    private static Random random;
    private Server[] servers;
    private Server feedback;
    private Server sandbox;
    private Server specified;
    private Server emq;
    private Server messageGlobal;
    private Server feedbackGlobal;
    private Server messageEurope;
    private Server messageVip;
    private Server messageGlobalVip;
    private Server feedbackEurope;
    private Server messageRussia;
    private Server feedbackRussia;
    private Server messageIndia;
    private Server feedbackIndia;
    private boolean inited;
    private long lastRefreshTime;
    
    private ServerSwitch() {
        this.feedback = new Server(Constants.HOST_PRODUCTION_FEEDBACK, 100, 100, 0, 0);
        this.sandbox = new Server(Constants.HOST_SANDBOX, 100, 100, 0, 0);
        this.specified = new Server(Constants.host, 100, 100, 0, 0);
        this.emq = new Server(Constants.HOST_EMQ, 100, 100, 0, 0);
        this.messageGlobal = new Server(Constants.HOST_GLOBAL_PRODUCTION, 100, 100, 0, 0);
        this.feedbackGlobal = new Server(Constants.HOST_GLOBAL_PRODUCTION_FEEDBACK, 100, 100, 0, 0);
        this.messageEurope = new Server(Constants.HOST_EUROPE_PRODUCTION, 100, 100, 0, 0);
        this.messageVip = new Server(Constants.HOST_VIP, 100, 100, 0, 0);
        this.messageGlobalVip = new Server(Constants.HOST_GLOBAL_VIP, 100, 100, 0, 0);
        this.feedbackEurope = new Server(Constants.HOST_EUROPE_PRODUCTION_FEEDBACK, 100, 100, 0, 0);
        this.messageRussia = new Server(Constants.HOST_RUSSIA_PRODUCTION, 100, 100, 0, 0);
        this.feedbackRussia = new Server(Constants.HOST_RUSSIA_PRODUCTION_FEEDBACK, 100, 100, 0, 0);
        this.messageIndia = new Server(Constants.HOST_INDIA_PRODUCTION, 100, 100, 0, 0);
        this.feedbackIndia = new Server(Constants.HOST_INDIA_PRODUCTION_FEEDBACK, 100, 100, 0, 0);
        this.inited = false;
        this.lastRefreshTime = System.currentTimeMillis();
    }
    
    public static ServerSwitch getInstance() {
        return ServerSwitch.INSTANCE;
    }
    
    static String buildFullRequestURL(final Server server, final Constants.RequestPath requestPath) {
        return Constants.HTTP_PROTOCOL + "://" + server.getHost() + requestPath.getPath();
    }
    
    public boolean needRefreshHostList() {
        return !this.inited || System.currentTimeMillis() - this.lastRefreshTime >= 300000L;
    }
    
    synchronized void initialize(final String values) {
        if (!this.needRefreshHostList()) {
            return;
        }
        final String[] vs = values.split(",");
        final Server[] servers = new Server[vs.length];
        int i = 0;
        for (final String s : vs) {
            final String[] sp = s.split(":");
            if (sp.length < 5) {
                servers[i++] = ServerSwitch.defaultServer;
            }
            else {
                try {
                    servers[i] = new Server(sp[0], Integer.valueOf(sp[1]), Integer.valueOf(sp[2]), Integer.valueOf(sp[3]), Integer.valueOf(sp[4]));
                }
                catch (Throwable e) {
                    servers[i] = ServerSwitch.defaultServer;
                }
                if (this.servers != null) {
                    for (final Server srv : this.servers) {
                        if (srv.getHost().equals(servers[i].getHost())) {
                            servers[i].priority.set(srv.getPriority());
                        }
                    }
                }
                ++i;
            }
        }
        this.inited = true;
        this.lastRefreshTime = System.currentTimeMillis();
        this.servers = servers;
    }
    
    Server selectServer(final Constants.RequestPath requestPath, final Region region, final boolean isVip) {
        if (Constants.host != null) {
            return this.specified.setHost(Constants.host);
        }
        if (Constants.sandbox) {
            return this.sandbox;
        }
        switch (requestPath.getRequestType()) {
            case FEEDBACK: {
                switch (region) {
                    case Europe: {
                        return this.feedbackEurope;
                    }
                    case Russia: {
                        return this.feedbackRussia;
                    }
                    case India: {
                        return this.feedbackIndia;
                    }
                    case Other: {
                        return this.feedbackGlobal;
                    }
                    default: {
                        return this.feedback;
                    }
                }
                break;
            }
            case EMQ: {
                return this.emq;
            }
            default: {
                if (isVip) {
                    switch (region) {
                        case Europe:
                        case Russia:
                        case India:
                        case Other: {
                            return this.messageGlobalVip;
                        }
                        default: {
                            return this.messageVip;
                        }
                    }
                }
                else {
                    switch (region) {
                        case Europe: {
                            return this.messageEurope;
                        }
                        case Russia: {
                            return this.messageRussia;
                        }
                        case India: {
                            return this.messageIndia;
                        }
                        case Other: {
                            return this.messageGlobal;
                        }
                        default: {
                            return this.selectServer();
                        }
                    }
                }
                break;
            }
        }
    }
    
    private Server selectServer() {
        if (!Constants.autoSwitchHost || !this.inited) {
            return ServerSwitch.defaultServer;
        }
        int allPriority = 0;
        final int[] priority = new int[this.servers.length];
        for (int i = 0; i < this.servers.length; ++i) {
            priority[i] = this.servers[i].getPriority();
            allPriority += priority[i];
        }
        final int randomPoint = ServerSwitch.random.nextInt(allPriority);
        int sum = 0;
        for (int j = 0; j < priority.length; ++j) {
            sum += priority[j];
            if (randomPoint <= sum) {
                return this.servers[j];
            }
        }
        return ServerSwitch.defaultServer;
    }
    
    static {
        ServerSwitch.defaultServer = new Server(Constants.HOST_PRODUCTION, 1, 90, 10, 5);
        ServerSwitch.INSTANCE = new ServerSwitch();
        ServerSwitch.random = new Random(System.currentTimeMillis());
    }
    
    public static class Server
    {
        private String host;
        private AtomicInteger priority;
        private int minPriority;
        private int maxPriority;
        private int decrStep;
        private int incrStep;
        
        Server(final String host, final int minPriority, final int maxPriority, final int decrStep, final int incrStep) {
            this.host = host;
            this.priority = new AtomicInteger(maxPriority);
            this.maxPriority = maxPriority;
            this.minPriority = minPriority;
            this.decrStep = decrStep;
            this.incrStep = incrStep;
        }
        
        String getHost() {
            return this.host;
        }
        
        Server setHost(final String host) {
            this.host = host;
            return this;
        }
        
        int getPriority() {
            return this.priority.get();
        }
        
        void incrPriority() {
            this.changePriority(true, this.incrStep);
        }
        
        void decrPriority() {
            this.changePriority(false, this.decrStep);
        }
        
        private void changePriority(final boolean incr, final int step) {
            int old;
            int newValue;
            do {
                old = this.priority.get();
                newValue = (incr ? (old + step) : (old - step));
                if (newValue < this.minPriority) {
                    newValue = this.minPriority;
                }
                if (newValue > this.maxPriority) {
                    newValue = this.maxPriority;
                }
            } while (!this.priority.compareAndSet(old, newValue));
        }
        
        @Override
        public String toString() {
            return this.host + ":<" + this.minPriority + "," + this.maxPriority + ">+" + this.incrStep + "-" + this.decrStep + ":" + this.priority;
        }
    }
}
