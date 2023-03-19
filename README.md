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
  

-------

- 별개로 GraphDB 관련 학습 정리
  <details>
  <summary>GraphDB 관련 </summary>

    - Graph : Vertex & Edge
    - GraphDB
        - Native Graph Storage : 그래프 저장, 처리에 있어 최적화
        - Index-free Adjacency : 연결된 노드는 DB에서 서로를 가리키고 있음
            - 인덱스에 자유롭게 접근 가능. 데이터가 많아져도 접근 속도 유지.
        - Relationship
            - 관계 표현 / Label, Property, Directionality를 가짐
        - 장점
            - 성능
                - RDB는 데이터 크기가 커질수록 join 비용이 커짐. 대부분의 NoSQL도 연결되지 않은 문서 집합을 저장해 애플리케이션 수준에서 집계를 조인해야 하므로 비용이 큼
                - 반면 Graph DB는 데이터의 크기가 커져도 퍼포먼스가 일관적임. 전체 그래프 사이즈에 대한 쿼리가 실행되는 게 아니라 그 쿼리가 순회하는 그래프에 대해서만 쿼리가 실행됨
            - Flexibility & Agility
                - RDB처럼 미리 요구사항을 예상해서 설계할 필요가 없음
    - RDB vs GraphDB

      | RDBMS | Graph DB |
      | --- | --- |
      | 테이블 | 레이블 |
      | 행 | 노드 |
      | 외래키 | 관계 |
      | 열 | 속성 |
      | 조인 | 순회(traversal) - beginning at a defined start vertex and ends at a defined depth with the end vertex. |
      | 열에는 반드시 필드값을 가지고 있어야 한다. | 동일한 레이블을 가진 노드는 동일한 속성을 가질 필요가 없다. |
      | Join 을 할 때, 계산된다. | 관계는 생성될 때, 디스크에 저장된다. |

  </details>
