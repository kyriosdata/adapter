# adapter
Adaptador do Modelo de Referência (MR) do openEHR para Seed.

[<img src="https://api.travis-ci.org/kyriosdata/adapter.svg?branch=master">](https://travis-ci.org/kyriosdata/adapter)
[![Dependency Status](https://www.versioneye.com/user/projects/581cb3834304530ad3a5549b/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/581cb3834304530ad3a5549b)
[![Sonarqube](https://sonarqube.com/api/badges/gate?key=com.github.kyriosdata.adapter%3Aoe-seed)](https://sonarqube.com/dashboard/index?id=com.github.kyriosdata.adapter%3Aoe-seed)
[![Javadocs](http://javadoc.io/badge/com.github.kyriosdata.adapter/oe-seed.svg)](http://javadoc.io/doc/com.github.kyriosdata.adapter/oe-seed)

<br />
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">
<img alt="Creative Commons License" style="border-width:0"
 src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
 <br />This work is licensed under a <a rel="license" 
 href="http://creativecommons.org/licenses/by/4.0/">Creative Commons 
 Attribution 4.0 International License</a>. 
 <br />Fábio Nogueira de Lucena - Fábrica de Software - 
 Instituto de Informática (UFG).

## Caso de uso
O Modelo de Referência (MR) do openEHR é um conjunto de classes que 
define os blocos básicos para empacotamento de informações em saúde.
Quando essas informações precisam ser transferidas ou mesmo persitidas,
existem várias possibilidades.  

## Como usar (via maven)?

Acrescente a dependência no arquivo pom.xml:

<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.kyriosdata.adapter&lt;/groupId&gt;
  &lt;artifactId&gt;oe-seed&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</pre>

#### Tipos

| Tipo | Valor |   Descrição  |
|:----:|:-----:|:------------:|
| 0x00 |   0   | bool (false) |
| 0x01 |   1   |  bool (true) |
|      |       |              |

#### Registros
Registros são sequências de campos, cada um deles de um determinado tipo.
A tabela de registros a ser contemplada pelo adatpador segue abaixo. 


### Formato de empacotamento (serialização)
A serialização de um objeto inclui os dados correspondentes ao _header_ 
seguidos dos dados correspondentes aos campos do objeto, nessa ordem,
conforme ilustrado abaixo.

````
+-----------------+
| Header | Fields |
+-----------------+
````

#### Header
Há um conjunto restrito de pouco mais de uma centana de classes (objetos).
Ou seja, um único byte é suciente para identificar o tipo (_Type_) do objeto.

````
+-------------+
| Type | Size |
+-------------+
````
