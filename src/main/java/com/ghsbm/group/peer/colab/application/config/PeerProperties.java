package com.ghsbm.group.peer.colab.application.config;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "peer", ignoreUnknownFields = false)
public class PeerProperties {

  private final OAuth2 oauth2 = new OAuth2();

  private final Async async = new Async();

  private final Http http = new Http();

  private final Database database = new Database();

  private final Cache cache = new Cache();

  private final Mail mail = new Mail();

  private final Security security = new Security();

  private final ApiDocs apiDocs = new ApiDocs();

  private final Logging logging = new Logging();

  private final CorsConfiguration cors = new CorsConfiguration();

  private final Social social = new Social();

  private final Gateway gateway = new Gateway();

  private final Registry registry = new Registry();

  private final ClientApp clientApp = new ClientApp();

  private final AuditEvents auditEvents = new AuditEvents();

  /**
   * Getter for the field <code>async</code>.
   *
   * @return a {@link PeerProperties.Async} object.
   */
  public Async getAsync() {
    return async;
  }

  /**
   * Getter for the field <code>http</code>.
   *
   * @return a {@link PeerProperties.Http} object.
   */
  public Http getHttp() {
    return http;
  }

  /**
   * Getter for the field <code>database</code>.
   *
   * @return a {@link PeerProperties.Database} object.
   */
  public Database getDatabase() {
    return database;
  }

  /**
   * Getter for the field <code>cache</code>.
   *
   * @return a {@link PeerProperties.Cache} object.
   */
  public Cache getCache() {
    return cache;
  }

  /**
   * Getter for the field <code>mail</code>.
   *
   * @return a {@link PeerProperties.Mail} object.
   */
  public Mail getMail() {
    return mail;
  }

  /**
   * Getter for the field <code>registry</code>.
   *
   * @return a {@link PeerProperties.Registry} object.
   */
  public Registry getRegistry() {
    return registry;
  }

  /**
   * Getter for the field <code>security</code>.
   *
   * @return a {@link PeerProperties.Security} object.
   */
  public Security getSecurity() {
    return security;
  }

  /**
   * Getter for the field <code>api-docs</code>.
   *
   * @return a {@link PeerProperties.ApiDocs} object.
   */
  public ApiDocs getApiDocs() {
    return apiDocs;
  }

  /**
   * Getter for the field <code>logging</code>.
   *
   * @return a {@link PeerProperties.Logging} object.
   */
  public Logging getLogging() {
    return logging;
  }

  /**
   * Getter for the field <code>cors</code>.
   *
   * @return a {@link org.springframework.web.cors.CorsConfiguration} object.
   */
  public CorsConfiguration getCors() {
    return cors;
  }

  /**
   * Getter for the field <code>social</code>.
   *
   * @return a {@link PeerProperties.Social} object.
   */
  public Social getSocial() {
    return social;
  }

  /**
   * Getter for the field <code>gateway</code>.
   *
   * @return a {@link PeerProperties.Gateway} object.
   */
  public Gateway getGateway() {
    return gateway;
  }

  /**
   * Getter for the field <code>clientApp</code>.
   *
   * @return a {@link PeerProperties.ClientApp} object.
   */
  public ClientApp getClientApp() {
    return clientApp;
  }

  /**
   * Getter for the field <code>auditEvents</code>.
   *
   * @return a {@link PeerProperties.AuditEvents} object.
   */
  public AuditEvents getAuditEvents() {
    return auditEvents;
  }

  public static class Async {

    private int corePoolSize = PeerDefaults.Async.corePoolSize;

    private int maxPoolSize = PeerDefaults.Async.maxPoolSize;

    private int queueCapacity = PeerDefaults.Async.queueCapacity;

    public int getCorePoolSize() {
      return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
      return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
      this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
      return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
      this.queueCapacity = queueCapacity;
    }
  }

  public static class Http {

    private final Cache cache = new Cache();

    public Cache getCache() {
      return cache;
    }

    public static class Cache {

      private int timeToLiveInDays = PeerDefaults.Http.Cache.timeToLiveInDays;

      public int getTimeToLiveInDays() {
        return timeToLiveInDays;
      }

      public void setTimeToLiveInDays(int timeToLiveInDays) {
        this.timeToLiveInDays = timeToLiveInDays;
      }
    }
  }

  public static class Database {

    private final Couchbase couchbase = new Couchbase();

    public Couchbase getCouchbase() {
      return couchbase;
    }

    public static class Couchbase {

      private String bucketName;

      private String scopeName;

      public String getBucketName() {
        return bucketName;
      }

      public Couchbase setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
      }

      public String getScopeName() {
        return scopeName;
      }

      public Couchbase setScopeName(String scopeName) {
        this.scopeName = scopeName;
        return this;
      }
    }
  }

  public static class Cache {

    private final Hazelcast hazelcast = new Hazelcast();

    private final Caffeine caffeine = new Caffeine();

    private final Ehcache ehcache = new Ehcache();

    private final Infinispan infinispan = new Infinispan();

    private final Memcached memcached = new Memcached();

    private final Redis redis = new Redis();

    public Hazelcast getHazelcast() {
      return hazelcast;
    }

    public Caffeine getCaffeine() {
      return caffeine;
    }

    public Ehcache getEhcache() {
      return ehcache;
    }

    public Infinispan getInfinispan() {
      return infinispan;
    }

    public Memcached getMemcached() {
      return memcached;
    }

    public Redis getRedis() {
      return redis;
    }

    public static class Hazelcast {

      private int timeToLiveSeconds = PeerDefaults.Cache.Hazelcast.timeToLiveSeconds;

      private int backupCount = PeerDefaults.Cache.Hazelcast.backupCount;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public int getBackupCount() {
        return backupCount;
      }

      public void setBackupCount(int backupCount) {
        this.backupCount = backupCount;
      }
    }

    public static class Caffeine {

      private int timeToLiveSeconds = PeerDefaults.Cache.Caffeine.timeToLiveSeconds;

      private long maxEntries = PeerDefaults.Cache.Caffeine.maxEntries;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public long getMaxEntries() {
        return maxEntries;
      }

      public void setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
      }
    }

    public static class Ehcache {

      private int timeToLiveSeconds = PeerDefaults.Cache.Ehcache.timeToLiveSeconds;

      private long maxEntries = PeerDefaults.Cache.Ehcache.maxEntries;

      public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
      }

      public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
      }

      public long getMaxEntries() {
        return maxEntries;
      }

      public void setMaxEntries(long maxEntries) {
        this.maxEntries = maxEntries;
      }
    }

    public static class Infinispan {

      private String configFile = PeerDefaults.Cache.Infinispan.configFile;

      private boolean statsEnabled = PeerDefaults.Cache.Infinispan.statsEnabled;

      private final Local local = new Local();

      private final Distributed distributed = new Distributed();

      private final Replicated replicated = new Replicated();

      public String getConfigFile() {
        return configFile;
      }

      public void setConfigFile(String configFile) {
        this.configFile = configFile;
      }

      public boolean isStatsEnabled() {
        return statsEnabled;
      }

      public void setStatsEnabled(boolean statsEnabled) {
        this.statsEnabled = statsEnabled;
      }

      public Local getLocal() {
        return local;
      }

      public Distributed getDistributed() {
        return distributed;
      }

      public Replicated getReplicated() {
        return replicated;
      }

      public static class Local {

        private long timeToLiveSeconds = PeerDefaults.Cache.Infinispan.Local.timeToLiveSeconds;

        private long maxEntries = PeerDefaults.Cache.Infinispan.Local.maxEntries;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }
      }

      public static class Distributed {

        private long timeToLiveSeconds =
            PeerDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;

        private long maxEntries = PeerDefaults.Cache.Infinispan.Distributed.maxEntries;

        private int instanceCount = PeerDefaults.Cache.Infinispan.Distributed.instanceCount;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }

        public int getInstanceCount() {
          return instanceCount;
        }

        public void setInstanceCount(int instanceCount) {
          this.instanceCount = instanceCount;
        }
      }

      public static class Replicated {

        private long timeToLiveSeconds = PeerDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;

        private long maxEntries = PeerDefaults.Cache.Infinispan.Replicated.maxEntries;

        public long getTimeToLiveSeconds() {
          return timeToLiveSeconds;
        }

        public void setTimeToLiveSeconds(long timeToLiveSeconds) {
          this.timeToLiveSeconds = timeToLiveSeconds;
        }

        public long getMaxEntries() {
          return maxEntries;
        }

        public void setMaxEntries(long maxEntries) {
          this.maxEntries = maxEntries;
        }
      }
    }

    public static class Memcached {

      private boolean enabled = PeerDefaults.Cache.Memcached.enabled;

      /** Comma or whitespace separated list of servers' addresses. */
      private String servers = PeerDefaults.Cache.Memcached.servers;

      private int expiration = PeerDefaults.Cache.Memcached.expiration;

      private boolean useBinaryProtocol = PeerDefaults.Cache.Memcached.useBinaryProtocol;

      private Authentication authentication = new Authentication();

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getServers() {
        return servers;
      }

      public void setServers(String servers) {
        this.servers = servers;
      }

      public int getExpiration() {
        return expiration;
      }

      public void setExpiration(int expiration) {
        this.expiration = expiration;
      }

      public boolean isUseBinaryProtocol() {
        return useBinaryProtocol;
      }

      public void setUseBinaryProtocol(boolean useBinaryProtocol) {
        this.useBinaryProtocol = useBinaryProtocol;
      }

      public Authentication getAuthentication() {
        return authentication;
      }

      public static class Authentication {

        private boolean enabled = PeerDefaults.Cache.Memcached.Authentication.enabled;
        private String username;
        private String password;

        public boolean isEnabled() {
          return enabled;
        }

        public Authentication setEnabled(boolean enabled) {
          this.enabled = enabled;
          return this;
        }

        public String getUsername() {
          return username;
        }

        public Authentication setUsername(String username) {
          this.username = username;
          return this;
        }

        public String getPassword() {
          return password;
        }

        public Authentication setPassword(String password) {
          this.password = password;
          return this;
        }
      }
    }

    public static class Redis {
      private String[] server = PeerDefaults.Cache.Redis.server;
      private int expiration = PeerDefaults.Cache.Redis.expiration;
      private boolean cluster = PeerDefaults.Cache.Redis.cluster;
      private int connectionPoolSize = PeerDefaults.Cache.Redis.connectionPoolSize;
      private int connectionMinimumIdleSize = PeerDefaults.Cache.Redis.connectionMinimumIdleSize;
      private int subscriptionConnectionPoolSize =
          PeerDefaults.Cache.Redis.subscriptionConnectionPoolSize;
      private int subscriptionConnectionMinimumIdleSize =
          PeerDefaults.Cache.Redis.subscriptionConnectionMinimumIdleSize;

      public String[] getServer() {
        return server;
      }

      public void setServer(String[] server) {
        this.server = server;
      }

      public int getExpiration() {
        return expiration;
      }

      public void setExpiration(int expiration) {
        this.expiration = expiration;
      }

      public boolean isCluster() {
        return cluster;
      }

      public void setCluster(boolean cluster) {
        this.cluster = cluster;
      }

      public int getConnectionPoolSize() {
        return connectionPoolSize;
      }

      public Redis setConnectionPoolSize(int connectionPoolSize) {
        this.connectionPoolSize = connectionPoolSize;
        return this;
      }

      public int getConnectionMinimumIdleSize() {
        return connectionMinimumIdleSize;
      }

      public Redis setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
        this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        return this;
      }

      public int getSubscriptionConnectionPoolSize() {
        return subscriptionConnectionPoolSize;
      }

      public Redis setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
        this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        return this;
      }

      public int getSubscriptionConnectionMinimumIdleSize() {
        return subscriptionConnectionMinimumIdleSize;
      }

      public Redis setSubscriptionConnectionMinimumIdleSize(
          int subscriptionConnectionMinimumIdleSize) {
        this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        return this;
      }
    }
  }

  @Value("${cors.allowedOrigins}")
  public String[] allowedOrigins;

  public OAuth2 getOauth2() {
    return oauth2;
  }

  public static final class OAuth2 {
    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
      return authorizedRedirectUris;
    }

    public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
      this.authorizedRedirectUris = authorizedRedirectUris;
      return this;
    }
  }

  public static class Mail {

    private boolean enabled = PeerDefaults.Mail.enabled;

    private String from = PeerDefaults.Mail.from;

    private String baseUrl = PeerDefaults.Mail.baseUrl;

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }
  }

  public static class Security {

    private String contentSecurityPolicy = PeerDefaults.Security.contentSecurityPolicy;

    private final ClientAuthorization clientAuthorization = new ClientAuthorization();

    private final Authentication authentication = new Authentication();

    private final RememberMe rememberMe = new RememberMe();

    private final OAuth2 oauth2 = new OAuth2();

    public ClientAuthorization getClientAuthorization() {
      return clientAuthorization;
    }

    public Authentication getAuthentication() {
      return authentication;
    }

    public RememberMe getRememberMe() {
      return rememberMe;
    }

    public OAuth2 getOauth2() {
      return oauth2;
    }

    public String getContentSecurityPolicy() {
      return contentSecurityPolicy;
    }

    public void setContentSecurityPolicy(String contentSecurityPolicy) {
      this.contentSecurityPolicy = contentSecurityPolicy;
    }

    public static class ClientAuthorization {

      private String accessTokenUri = PeerDefaults.Security.ClientAuthorization.accessTokenUri;

      private String tokenServiceId = PeerDefaults.Security.ClientAuthorization.tokenServiceId;

      private String clientId = PeerDefaults.Security.ClientAuthorization.clientId;

      private String clientSecret = PeerDefaults.Security.ClientAuthorization.clientSecret;

      public String getAccessTokenUri() {
        return accessTokenUri;
      }

      public void setAccessTokenUri(String accessTokenUri) {
        this.accessTokenUri = accessTokenUri;
      }

      public String getTokenServiceId() {
        return tokenServiceId;
      }

      public void setTokenServiceId(String tokenServiceId) {
        this.tokenServiceId = tokenServiceId;
      }

      public String getClientId() {
        return clientId;
      }

      public void setClientId(String clientId) {
        this.clientId = clientId;
      }

      public String getClientSecret() {
        return clientSecret;
      }

      public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
      }
    }

    public static class Authentication {

      private final Jwt jwt = new Jwt();

      public Jwt getJwt() {
        return jwt;
      }

      public static class Jwt {

        private String secret = PeerDefaults.Security.Authentication.Jwt.secret;

        private String base64Secret = PeerDefaults.Security.Authentication.Jwt.base64Secret;

        private long tokenValidityInSeconds =
            PeerDefaults.Security.Authentication.Jwt.tokenValidityInSeconds;

        private long tokenValidityInSecondsForRememberMe =
            PeerDefaults.Security.Authentication.Jwt.tokenValidityInSecondsForRememberMe;

        public String getSecret() {
          return secret;
        }

        public void setSecret(String secret) {
          this.secret = secret;
        }

        public String getBase64Secret() {
          return base64Secret;
        }

        public void setBase64Secret(String base64Secret) {
          this.base64Secret = base64Secret;
        }

        public long getTokenValidityInSeconds() {
          return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
          this.tokenValidityInSeconds = tokenValidityInSeconds;
        }

        public long getTokenValidityInSecondsForRememberMe() {
          return tokenValidityInSecondsForRememberMe;
        }

        public void setTokenValidityInSecondsForRememberMe(
            long tokenValidityInSecondsForRememberMe) {
          this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
        }
      }
    }

    public static class RememberMe {

      @NotNull private String key = PeerDefaults.Security.RememberMe.key;

      public String getKey() {
        return key;
      }

      public void setKey(String key) {
        this.key = key;
      }
    }

    public static class OAuth2 {
      private List<String> audience = new ArrayList<>();

      public List<String> getAudience() {
        return Collections.unmodifiableList(audience);
      }

      public void setAudience(@NotNull List<String> audience) {
        this.audience.addAll(audience);
      }
    }
  }

  public static class ApiDocs {

    private String title = PeerDefaults.ApiDocs.title;

    private String description = PeerDefaults.ApiDocs.description;

    private String version = PeerDefaults.ApiDocs.version;

    private String termsOfServiceUrl = PeerDefaults.ApiDocs.termsOfServiceUrl;

    private String contactName = PeerDefaults.ApiDocs.contactName;

    private String contactUrl = PeerDefaults.ApiDocs.contactUrl;

    private String contactEmail = PeerDefaults.ApiDocs.contactEmail;

    private String license = PeerDefaults.ApiDocs.license;

    private String licenseUrl = PeerDefaults.ApiDocs.licenseUrl;

    private String[] defaultIncludePattern = PeerDefaults.ApiDocs.defaultIncludePattern;

    private String[] managementIncludePattern = PeerDefaults.ApiDocs.managementIncludePattern;

    private Server[] servers = {};

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public String getTermsOfServiceUrl() {
      return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
      this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContactName() {
      return contactName;
    }

    public void setContactName(String contactName) {
      this.contactName = contactName;
    }

    public String getContactUrl() {
      return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
      this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
      return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
      this.contactEmail = contactEmail;
    }

    public String getLicense() {
      return license;
    }

    public void setLicense(String license) {
      this.license = license;
    }

    public String getLicenseUrl() {
      return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
      this.licenseUrl = licenseUrl;
    }

    public String[] getDefaultIncludePattern() {
      return defaultIncludePattern;
    }

    public void setDefaultIncludePattern(String[] defaultIncludePattern) {
      this.defaultIncludePattern = defaultIncludePattern;
    }

    public String[] getManagementIncludePattern() {
      return managementIncludePattern;
    }

    public void setManagementIncludePattern(String[] managementIncludePattern) {
      this.managementIncludePattern = managementIncludePattern;
    }

    public Server[] getServers() {
      return servers;
    }

    public void setServers(Server[] servers) {
      this.servers = servers;
    }

    public static class Server {
      private String url;
      private String description;

      public String getUrl() {
        return url;
      }

      public void setUrl(String url) {
        this.url = url;
      }

      public String getDescription() {
        return description;
      }

      public void setDescription(String description) {
        this.description = description;
      }
    }
  }

  public static class Logging {

    private boolean useJsonFormat = PeerDefaults.Logging.useJsonFormat;

    private final Logstash logstash = new Logstash();

    public boolean isUseJsonFormat() {
      return useJsonFormat;
    }

    public void setUseJsonFormat(boolean useJsonFormat) {
      this.useJsonFormat = useJsonFormat;
    }

    public Logstash getLogstash() {
      return logstash;
    }

    public static class Logstash {

      private boolean enabled = PeerDefaults.Logging.Logstash.enabled;

      private String host = PeerDefaults.Logging.Logstash.host;

      private int port = PeerDefaults.Logging.Logstash.port;

      private int ringBufferSize = PeerDefaults.Logging.Logstash.ringBufferSize;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public String getHost() {
        return host;
      }

      public void setHost(String host) {
        this.host = host;
      }

      public int getPort() {
        return port;
      }

      public void setPort(int port) {
        this.port = port;
      }

      public int getRingBufferSize() {
        return ringBufferSize;
      }

      public void setRingBufferSize(int ringBufferSize) {
        this.ringBufferSize = ringBufferSize;
      }
    }
  }

  public static class Social {

    private String redirectAfterSignIn = PeerDefaults.Social.redirectAfterSignIn;

    public String getRedirectAfterSignIn() {
      return redirectAfterSignIn;
    }

    public void setRedirectAfterSignIn(String redirectAfterSignIn) {
      this.redirectAfterSignIn = redirectAfterSignIn;
    }
  }

  public static class Gateway {

    private final RateLimiting rateLimiting = new RateLimiting();

    public RateLimiting getRateLimiting() {
      return rateLimiting;
    }

    private Map<String, List<String>> authorizedMicroservicesEndpoints =
        PeerDefaults.Gateway.authorizedMicroservicesEndpoints;

    public Map<String, List<String>> getAuthorizedMicroservicesEndpoints() {
      return authorizedMicroservicesEndpoints;
    }

    public void setAuthorizedMicroservicesEndpoints(
        Map<String, List<String>> authorizedMicroservicesEndpoints) {
      this.authorizedMicroservicesEndpoints = authorizedMicroservicesEndpoints;
    }

    public static class RateLimiting {

      private boolean enabled = PeerDefaults.Gateway.RateLimiting.enabled;

      private long limit = PeerDefaults.Gateway.RateLimiting.limit;

      private int durationInSeconds = PeerDefaults.Gateway.RateLimiting.durationInSeconds;

      public boolean isEnabled() {
        return enabled;
      }

      public void setEnabled(boolean enabled) {
        this.enabled = enabled;
      }

      public long getLimit() {
        return limit;
      }

      public void setLimit(long limit) {
        this.limit = limit;
      }

      public int getDurationInSeconds() {
        return durationInSeconds;
      }

      public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
      }
    }
  }

  public static class Registry {

    private String password = PeerDefaults.Registry.password;

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }

  public static class ClientApp {

    private String name = PeerDefaults.ClientApp.name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class AuditEvents {
    private int retentionPeriod = PeerDefaults.AuditEvents.retentionPeriod;

    public int getRetentionPeriod() {
      return retentionPeriod;
    }

    public void setRetentionPeriod(int retentionPeriod) {
      this.retentionPeriod = retentionPeriod;
    }
  }
}
