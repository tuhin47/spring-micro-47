### STAGE 1: Build ###
FROM node:16-alpine AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
ARG ENVIRONMENT
ARG API_BASE
ARG OAUTH_REDIRECT_URL
COPY . .
RUN npm run build

### STAGE 2: Run ###
FROM nginx:1.20-alpine
COPY default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /usr/src/app/dist/easy-angular /usr/share/nginx/html
#COPY ./dist/easy-angular /usr/share/nginx/html
EXPOSE 80
