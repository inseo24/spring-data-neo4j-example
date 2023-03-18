# spring-data-neo4j-example


- 2023.3.16 대충 CRUD만 Spring Doc 보고 만들어 봄
- Docker 준비
  ```shell
  docker pull neo4j:latest

  # neo4j의 deafult username, password는 모두 neo4j임
  # 아래처럼 NEO4J_AUTH 에 원하는 비밀번호 작성 시 설정 가능
  docker run --name neo4j_container -p 7474:7474 -p 7687:7687 -e NEO4J_AUTH=neo4j/tjdls@1234 -d neo4j:latest
  ```
  
- 필요한 Dependency
  ```Groovy
  implementation("org.springframework.boot:spring-boot-starter-data-neo4j")
  implementation("org.neo4j.driver:neo4j-java-driver:5.6.0")
  ```
- application.yaml
  ```yaml
  
  spring:
  neo4j:
    uri: bolt://localhost:7687
    authentication:
      username: neo4j
      password: tjdls@1234
  
  ```

- 추후 추가할 내용
  - Relationship 설정에 대해 좀 더 공부해서 적용해 볼 예정. 당일은 단순 CRUD만 테스트 해 봄. 
