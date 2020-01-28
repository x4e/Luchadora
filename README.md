# Luchadora

A Minecraft player utility modification for forge 1.12.2.

I wrote this in java, then converted it to kotlin. The conversion process was the extent of my kotlin "learning" so please excuse the kotlin which probably isnt the best.

Please check LICENSE.MD.

## Cool Features
- Automatic Module detection through reflection
- Automatic Value detection through reflection
- Kotlin

## Contributing
I'm welcome to accept PRs. Please be civil and polite in the PR.

## Building
```
git clone https://github.com/cookiedragon234/Luchadora.git
.\gradlew setupDecompWorkspace
.\gradlew build
# Make any necessary changes to the code
# Test the changes
.\gradlew runClient
git add src/
git commit -m "Changed stuff"
git push -u origin master
```
