# Ambiente de desenvolvimento com hot reload
FROM node:20-alpine

RUN apk add --no-cache libc6-compat

WORKDIR /app

# Copia apenas arquivos de dependência para instalar primeiro (cache)
COPY package.json package-lock.json ./
RUN npm install

# Copia o resto do projeto
COPY . .

EXPOSE 3000

CMD ["npm", "run", "dev"]

