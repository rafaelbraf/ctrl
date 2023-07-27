FROM node:18
WORKDIR .
COPY package*.json ./
RUN yarn install
COPY . .
EXPOSE 8080
CMD ["yarn", "start"]