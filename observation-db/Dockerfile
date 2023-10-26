FROM postgres:latest
EXPOSE 5432

# Copy SQL scripts into the container's init directory
COPY db-script/*.sql /docker-entrypoint-initdb.d/