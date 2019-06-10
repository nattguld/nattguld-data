# nattguld-data
Handles various ways of local IO operations meant for data management.
JSON & binary data handling as focus on data management.
Also includes base structures for serialization, temporary containers, compression & caching.

## Dependencies
This repo uses gson as dependency for handling JSON data.
https://github.com/google/gson

## Examples
### Resource (Json)
```java
public class Example extends JsonResource() {

  private int someInt;

	public Example(JsonReader reader) {
		super(reader);
    
    this.someInt = getReader().getAsInt("some_int");
	}

	@Override
	public void write(JsonWriter writer) {
		writer.write("some_int", someInt);
	}

	@Override
	public String getSavePath() {
		return "./myresource.json";
	}
}
JsonReader reader = ResourceIO.loadJsonResource(MySaveFile);
Example ex = new Example(reader);
```

### Resource Manager (Json)
```java
public class TestManager extends JsonResourceManager<Example> {
	
	@Override
	protected Test instantiateResource(JsonReader reader) {
		return new Example(reader);
	}

	@Override
	protected String getStorageDirPath() {
		return "./";
	}
	
	@Override
	public List<Example> getResources() {
		return super.getResources();
	}
}
new TestManager().load();
```

### Caching
```java
FileCache fc = new FileCache(String cacheDirPath);
fc.clear();
File cachedFile = fc.getCached(File f); //Caches if not cached yet
```

### Compression
```java
Zipper.unzip(File zipFile, File outputDir);
Zipper.zip(File source, File destination);
```

### Serialization
```java
Serialization.serialize(Object o, String savePath);
Object o = Serialization.deserialize(String savePath);
```

### Workspace (temporary file management)
```java
Workspace.setBaseDirectory(String baseDirPath); //Modify the base dir for the temporary files

try (Workspace ws = new Workspace()) {
  File temp = ws.addToWorkSpace(String filePath);
  //Do stuff with temp
  File dir = ws.getWorkDir();
}
```
