FROM docker.elastic.co/elasticsearch/elasticsearch:7.11.2

# The --batch is required to bypass console interaction with use since this
# plugin require additionnal permissions
RUN bin/elasticsearch-plugin install --batch ingest-attachment
