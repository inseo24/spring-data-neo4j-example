# spring-data-neo4j-example


- 2023.3.16 대충 CRUD만 Spring Doc 보고 만들어 봄
- Docker 준비
  ```shell
  docker pull neo4j:latest

  # neo4j의 deafult username, password는 모두 neo4j임
  # 아래처럼 NEO4J_AUTH 에 원하는 비밀번호 작성 시 설정 가능
  docker run --name neo4j_container -p 7474:7474 -p 7687:7687 -e NEO4J_AUTH=neo4j/tjdls@1234 -d neo4j:latest
  ```
  
