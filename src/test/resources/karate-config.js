function fn() {

    var env = karate.env; // read environment variable
    karate.log('Running Karate tests in environment:', env);

    var config = {};

    if (!env) {
        env = 'dev';
    }

    if (env === 'dev') {
        config.baseUrl = 'http://localhost:8083/api/v1';
    }

    if (env === 'qa') {
        config.baseUrl = 'http://qa.mycompany.com/api/v1';
    }

    if (env === 'prod') {
        config.baseUrl = 'https://prod.mycompany.com/api/v1';
    }

    // global headers (optional)
    config.headers = { 'Content-Type': 'application/json' };

    return config;
}
