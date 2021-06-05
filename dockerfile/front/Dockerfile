#构建编译环境
FROM node:15.3
RUN npm config set registry https://registry.npm.taobao.org \
    && git clone https://gitee.com/tank59he/bjfu_contest_react.git /usr/src/bjfu_contest \
    && cd /usr/src/bjfu_contest \
    && npm install \
    && npm run build


FROM nginx:1.19
COPY --from=0 /usr/src/bjfu_contest/build /usr/src/bjfu_contest
COPY nginx.conf /etc/nginx/
EXPOSE 80