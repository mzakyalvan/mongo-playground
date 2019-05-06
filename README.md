# Read Me

Following instructions describe how to set up development environment.

## Mongodb Cluster

### Create `docker-compose.yml` file

```yaml
version: '3.7'
services:
  mongo1:
    image: mongo:4.0.9
    ports:
      - 30001:30001
    volumes:
      - ./mongocluster/mongo1:/data/db
    networks:
      - mongocluster
    command: mongod --replSet sample --port 30001 --smallfiles
  mongo2:
    image: mongo:4.0.9
    ports:
      - 30002:30002
    volumes:
      - ./mongocluster/mongo2:/data/db
    networks:
      - mongocluster
    command: mongod --replSet sample --port 30002 --smallfiles
    depends_on:
      - mongo1
  mongo3:
    image: mongo:4.0.9
    ports:
      - 30003:30003
    volumes:
      - ./mongocluster/mongo3:/data/db
    networks:
      - mongocluster
    command: mongod --replSet sample --port 30003 --smallfiles
    depends_on:
      - mongo2
networks:
  mongocluster:
    driver: bridge
```

### From first mongo node

> Log into first mongo node ```docker exec -it resources_mongo1_1 mongo --port 30001```

```sh
db = (new Mongo('localhost:30001')).getDB('test')
```

### Config for connecting three nodes together (executes this from one of node).
```sh
config = {
  "_id" : "trainsearch",
  "members": [
    {
      "_id": 0,
      "host": "mongo1:30001"
    },
    {
      "_id": 1,
      "host": "mongo2:30002"
    },
    {
      "_id": 2,
      "host": "mongo3:30003"
    }
  ]
}
```

### Initiate to start replicating data of replica set

```sh
rs.initiate(config)
```

### Update ```/etc/hosts``` entry

Map `mongo1`, `mongo2` and `mongo3` to `localhost` in `/etc/hosts`, for example

```sh
127.0.0.1   localhost mongo1 mongo2 mongo3
```