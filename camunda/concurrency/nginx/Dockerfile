FROM nginx:1.17.3

# Copier la configuration
COPY ./src/sslkeylog.c /usr/local/lib

# Configuration
RUN apt-get update && \
    apt-get -y install nginx-module-njs build-essential libssl-dev && \
    cc /usr/local/lib/sslkeylog.c -shared -o /usr/local/lib/libsslkeylog.so -fPIC -ldl && \
    ls /usr/local/lib

# For SSL pre-master key log
ENV LD_PRELOAD=/usr/local/lib/libsslkeylog.so
ENV SSLKEYLOGFILE=/etc/nginx/ssl-key.logs

# Run Nginx
CMD ["nginx-debug", "-g", "daemon off;"]
