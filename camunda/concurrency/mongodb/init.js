let res = [
    db.createCollection('UUIDCollection'),
    db.getCollection('UUIDCollection').createIndex({
        'uuid': 1
    }, {
        unique: true
    })
];

printjson(res)
