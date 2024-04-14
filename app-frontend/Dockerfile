# Stage 1: Build the Node.js application
FROM node:16-alpine as build
WORKDIR /app

# Copy package.json and package-lock.json (or yarn.lock)
COPY package*.json ./

# Install dependencies
RUN apk --no-cache add npm
RUN npm install

# Copy the rest of your application's code
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve the static files with Nginx
FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY conf.d/ /etc/nginx/conf.d/
WORKDIR /usr/share/nginx/html

# Copy the build output from the previous stage
COPY --from=build /app/dist .

# Expose port 80 to the outside
#EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
