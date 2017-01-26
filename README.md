# Petitii_IGPR_Backend
REST Api pentru petitii online Politie.

## Tehnologii folosite
- [RestExpress 0.11.3] (https://github.com/RestExpress/RestExpress)
- [MariaDB 10.1.20] (https://mariadb.org/)
- [Hibernate ORM 5.2.3] (http://hibernate.org/orm/)
- [Swagger] (http://swagger.io/)
- [Maven] (https://maven.apache.org/)

## Instalare

### Dependinte 
```
sudo apt-get update
```
#### Maven
```
sudo apt-get install maven
```
Verificare versiune Maven:
```
> mvn -v
Apache Maven 3.2.3 (33f8c3e1027c3ddde99d3cdebad2656a31e8fdf4; 2014-08-11T23:58:10+03:00)
```

#### Java OpenJDK 8
```
sudo apt-get install default-jdk
```
sau Oracle JDK 8

```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
```
Verificare versiune Java:
```
> java -version
openjdk version "1.8.0_111"
OpenJDK Runtime Environment (build 1.8.0_111-8u111-b14-2ubuntu0.16.04.2-b14)
OpenJDK 64-Bit Server VM (build 25.111-b14, mixed mode)

```

#### Mariadb(Mysql)
```
apt-get install mariadb-server
```
Securizare Mysql:
```
> mysql_secure_installation
- Remove anonymous users? [Y/n] y
- Disallow root login remotely? [Y/n] y
- Remove test database and access to it? [Y/n] y
- Reload privilege tables now? [Y/n] y
```
Pornire Mysql:
```
sudo service mariadb start
mysql -u root -p
```

### Import DB
```
mysql -u root -p < src/main/resources/sql/tickets_ddl.sql
mysql -u root -p tickets < src/main/resources/sql/counties_data.sql
```

## Configurare
Pentru fiecare environment exista cate un folder in: `config`: `config/prod`, `config/dev`, samd.
Modificarile probabile sunt cele legate de Mysql connection. Asadar editati `config/dev/hibernate.cfg.xml`:
```
<property name="connection.url">
  <![CDATA[jdbc:mysql://localhost:3306/tickets?autoReconnect=true&user=xxx&password=xxx]]>
</property>
```
Portul pe care va rula API se editeaza in `config/dev/environment.properties`:
```
port = 8082
```

## Impachetare
Din folderul root al repo-ului:
```
mvn -Pdev clean package -DskipTests
```

## Rulare
```
cd target
java -jar tickets-1.0-SNAPSHOT.jar dev
```


## Documentatie API
Folosim Swagger, care este generat automat de catre API folosind ruta: `http://localhost:8082/api-docs`
In folderul repo-ului exista si Swagger UI, in caz ca vreti sa testati local API deschideti in browser fisierul: `swagger/index.html`