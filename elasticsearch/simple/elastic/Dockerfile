FROM docker.elastic.co/elasticsearch/elasticsearch:6.8.0

# The --batch is required to bypass console interaction with use since this
# plugin require additionnal permissions
RUN bin/elasticsearch-plugin install --batch ingest-attachment
