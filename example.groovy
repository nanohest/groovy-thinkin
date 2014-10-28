def r = new RethinkConnection()
r.open('localhost', 28015)
println r.response

// DROP TABLE completesample
// [START,[TABLE_DROP,["completesample"]]]
r <<  "[1,[61,[\"completesample\"]]]"
println r.response

// CREATE TABLE completesample
// [START,[TABLE_CREATE,["completesample"]]]
r <<  "[1,[60,[\"completesample\"]]]"
println r.response


// INSERT INTO completesample (name, age) VALUES ("Kristian", 30)
// [START,[INSERT,[[TABLE,["completesample"]], {"name": "Kristian", "age":30}]]]
r << "[1,[56, [[15, [\"completesample\"]], {\"name\": \"Kristian\", \"age\": 30}]]]"
println r.response

// INSERT INTO completesample (name, version) VALUES ("Groovy rethink driver", "premature")
// [START,[INSERT,[[TABLE,["completesample"]], {"name": "Groovy rethink driver", "version": "premature}]]]
r << "[1,[56, [[15, [\"completesample\"]], {\"name\": \"Groovy rethink wire lib\", \"version\": \"toddler\" }]]]"
println r.response

// SELECT COUNT(*) FROM completesample
// [START,[COUNT,[[TABLE, ['completesample']]]]] 
r << "[1,[43, [[15, [\"completesample\"]]]]]"
println r.response

// SELECT * FROM completesample
// [START,[GET_ALL,[[TABLE.['completesample']]]]]
r << "[1,[15,[\"completesample\"]]]"
println r.response

r.close()
