FROM nginx:1.24

WORKDIR /tmp
COPY /swagger-ui-dist /usr/share/nginx/html
COPY /nginx.conf /etc/nginx/conf.d/default.conf

ENTRYPOINT ["nginx", "-g", "daemon off;"]

