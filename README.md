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

#### Tipos de campos
Um campo de um registro é definido pelo tipo correspondente. 
Os tipos disponíveis são definidos na tabela abaixo. Seguindo a
ordem das colunas abaixo tem-se o nome da constante que define o tipo, 
o valor dessa para o tipo e, por último, o tipo correspondente na
linguagem Java.

| Constante   | Valor |   Java   |
|:----:  |:-----:|:--------:|
| BOOL   |  0    |  boolean |
| BYTE   |  1    |  byte    |
| SHORT  |  2    |  short   |
| INT    |  3    |  int     |
| LONG   |  4    |  long    |
| FLOAT  |  5    |  float   |
| DOUBLE |  6    |  double  |
| CHAR   |  7    |  char    |
| STRING |  8    |  String  |
| VECTOR |  9    |  byte[]  |

#### Block
A classe _Block_ encapsula operações sobre um vetor de bytes (_buffer_) e 
possui métodos para depositar a partir de determinada posição os bytes 
correspondentes a cada um dos tipos acima e, no sentido inverso, a partir de determinada
posição, recuperar o valor depositado. Convém observar, contudo, que essa
classe não sabe o tipo de um valor depositado em uma dada posição. Essa
informação cabe a um registro. 

#### Formato de registro
O formato de um registro é definido por uma sequência de tipos de campos. 
Por exemplo, para um registro formado por dois valores lógicos a
sequência de tipos é dada por 00. Por outro lado, se o registro é definido 
por um INT e um VECTOR (vetor de bytes), então a sequência de tipos correspondente é 39. 
Observe que não se trata do número 39, mas da sequência do tipo INT (3) seguido do tipo 
VECTOR (9). Mais um exemplo: o registro formado por três sequências de caracteres (STRING) 
seguidas por um DOUBLE tem como formato 8886.

#### Tamanho de um registro
Se o formato de um registro não inclui os tipos de valores 8 e 9, respectivamente,
STRING e VECTOR, então o registro possui tamanho fixo. Caso contrário, varia o tamanho 
do registro que possui pelo menos um campo de um desses tipos. Por exemplo, o formato
de registro definido por uma única STRING que deve registrar um logradouro, em um
registro pode ter como valor "Avenida T2", em outro registro "Avenida Paulista", de 
tamanho distinto. 

#### Representação (serialização) de um registro
Um registro é representado por um _header_ seguido dos dados correspondentes aos 
tipos do registro, conforme ilustrado abaixo.

```
+-----------------+
| Header | Fields |
+-----------------+
```

O _header_ inclui obrigatoriamente um único valor: o tipo que unicamente identifica
o formato do registro. Observe que não é o formato propriamente dito, mas um
identificador que permite localizar o formato empregado pelo registro. Observe que
em alguns casos esse único valor é suficiente para identificar todos os demais
valores do _header_ do registro em questão. Ou seja, trata-se de um caso particular.
Em muitos outros, o _header_ deve conter outras
informações:
- tipo.
- tamanho do registro. Permite rapidamente "saltar" para o próximo registro. 
- posições ("apontadores") para campos do formato do registro que seguem campos de tamanho variável.

Abaixo segue a ilustração de um registro cujo formato é 388, ou seja, um INT seguido de
duas sequências de caracteres (STRING). 

```
+--------------------+
|54| nome | endereço |
+--------------------+
```

Há um conjunto restrito de pouco mais de uma centana de classes (objetos).
Ou seja, um único byte é suciente para identificar o tipo (_Type_) do objeto.

````
+-------------+
| Type | Size |
+-------------+
````


| Tipo   | MR (openEHR) | Campos | Comentário |
|:----:  |----------------------|:------:|------------|
| 0      |  DV_BOOLEAN          |  - |  Valor lógico _false_ |
| 1      |  DV_BOOLEAN          |  - |  Valor lógico _true_    |
| 2      |  DV_IDENTIFIER       |  8 (id), 8 (type), 8 (issuer), 8 (assigner) |    |


##### DV_IDENTIFIER (header)


| Ordem  | Tipo |   Java   |
|:----:  |:-----:|:--------:|
| 0      |  2    |  boolean |

