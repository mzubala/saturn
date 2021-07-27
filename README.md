### Running the app

To run build with tests:

```
./gradlew build
```

To run the app (port 8080 must be available):

```
./gradlew bootRun
```

### Sample requests to test.

The inmemory H2 database is populated on startup with some test data, so you can run sample requests right away.
The db is volatile, so after app restart everything starts from scratch.

Get cart:

```
 curl http://localhost:8080/carts/1 -v
```

Get recipes:

```
 curl http://localhost:8080/recipes -v
```

Add recipe:

```
curl -X POST http://localhost:8080/carts/1/add_recipe -d '{"receipeId": 1}' -v -H "Content-type: application/json"
```

Delete recipe:

```
curl -X DELETE http://localhost:8080/carts/1/delete_recipe/1 -v
```