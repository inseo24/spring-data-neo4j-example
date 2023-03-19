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

- 2023.3.17 추가
  - Relationship 에서 Direction은 노드가 연결된 이유를 모델링하는데 사용. 
  - Neo4j에는 2가지 type이 존재 : `OUTGOING`과 `INCOMING`
    - 명시적인 UNDIRECTED 또는 DIRECTED 은 없음
    - `OUTGOING`: 현재 노드에서 시작하여 다른 노드로 이동하는 관계. OUTGOING 방향의 관계를 쿼리할 때는 현재 노드가 source인 관계를 찾음
    - `INCOMING`: 다른 노드에서 시작하여 현재 노드로 이동하는 관계. INCOMING 방향의 관계를 쿼리할 때는 현재 노드가 target인 관계를 찾음
    - Direction 설정은 모델링하는 도메인과 노드 간의 관계 유형에 따라 달라진다는 점에 유의하자. 어떤 경우에는 Direction이 중요할 수 있지만(예: 소셜 미디어의 "FOLLOWS" 관계), 어떤 경우에는 Direction이 중요하지 않을 수 있음(예: "FRIENDS_WITH"). 후자의 경우, 예시와 같이 양방향으로 관계를 생성하여 관계를 방향이 없는 것으로 모델링 가능.
  - 하나 더 유의할 사항은 관계는 DB에 2번 저장되므로 관계를 쿼리할 때 필요한 경우 중복을 처리해야 할 수도 있음
  
-------- 

  - 고민할 사항
    - Datagrip에서는 Neo4j 같은 GraphDB를 지원하지 않음
      - Neo4j Broswer나 Neo4j Bloom을 사용해야 함
        - Neo4j Broswe의 경우 아래처럼 사용 가능
          - http://localhost:7474/ 에 접속 후 username & password 방식으로 접속
      - 모든 노드와 관계를 조회하려면 다음 Cypher 쿼리를 사용하면 된다.
      ```cypher
      MATCH (n)-[r]->(m) RETURN n, r, m;
      ```
      
      - 조회 결과
        
        <img width="531" alt="image" src="https://user-images.githubusercontent.com/84627144/226150494-0ed22c85-2e7c-4cfa-99fc-aa5cf0fc205a.png">
      
    - 양방향 관계는 순환 참조가 발생하는데 이걸 어떻게 처리할 지 고민 필요
    - Neo4j의 ID는 Long 사용이 deprecated 됐나 봄. 실행 시 로그에 아래처럼 나옴.
    ```plain text
      Neo4jPersistentEntity  : The entity com.seoin.springdataneo4jexample.Person is using a Long value for storing internally generated Neo4j ids. 
      The Neo4j internal Long Ids are deprecated, please consider using an external ID generator.
    ```
  
