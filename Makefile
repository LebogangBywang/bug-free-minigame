checks:
	@mvn verify

test_ref:
	@java -jar .libs/reference-server-0.1.0.jar &
	@mvn test


deploy:
	@cd Server && docker build -t server:1.0.1 .
	@docker login -u gitlab+deploy-token-13 -p g732znJoLW7fTFxc3se- git@github.com:LebogangBywang/MiniGame.git:5050 &
	@docker tag server:1.0.1 git@github.com:5050/LebogangBywang/MiniGame &
	@docker push git@github.com:LebogangBywang/MiniGame.git && docker run -p 5000:5000 server:1.0.1

clean:
	@mvn clean

package:
	@mvn package -DskipTests

bumpRootPatch:
	@mvn validate -DbumpPatch

bumpRootMinor: 
	@mvn validate -DbumpMinor

bumpRootMajor:
	@mvn validate -DbumpMajor

