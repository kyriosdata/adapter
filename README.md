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

### Documentação

#### Tipos (dos campos)
Os tipos contemplados seguem abaixo, identificados pelo correspondente tipo em Java.

- Os tipos inteiros: BYTE (byte), INT (int) (32 bits), INT64 (long) (64 bits).
- Os tipos em ponto flutuante: REAL (float) (32 bits), DOUBLE (double) (64 bits).
- O tipo lógico: BOOLEAN (boolean).
- O tipo que representa um caractere: CHAR (char).
- O tipo sequência de caracteres: STRING (String).
- O tipo vetor de bytes: VECTOR (byte[]).
- O tipo intervalo: INTERVAL_INT, INTERVAL_INT64, INTERVAL_REAL, INTERVAL_DOUBLE. Cada um desses tipos é formado por quatro valores. Dois para os limites do intervalo, _lower_ e _upper_, e outros dois lógicos, _lowerIncluded_ e _upperIncluded_. Os tipos dos limites do intervalo são definidos pelo tipo do intervalo, por exemplo, INTERVAL_INT faz uso de dois inteiros, enquanto INTERVAL_DOUBLE faz uso de dois valores do tipo ponto-flutuante de precisão dupla.
- O tipo lista: LIST (List). Uma lista é uma coleção de itens, não únicos, mas em uma ordem. 
- O tipo conjunto: SET (Set). É uma coleção de itens únicos, não podem existir repetições, não há ordem entre eles.
- O tipo dicionário: HASH (Hash). É um dicionário ou mapa, ou seja, uma coleção de valores, cada um deles disponível e associado a uma dada chave.

#### Tamanho de um registro
Um registro é uma combinação de campos. O tamanho de um registro, portanto, depende da quantidade de bytes necessária para armazenar cada um dos campos do registro. Se um registro é formado exclusivamente por campos de tamanho fixo, então o registro possui tamanho fixo. 
Caso contrário, o tamanho do registro varia. Ou seja,
no pode ser definido antecipadamente e, é único por registro. Por exemplo, o formato
de registro definido por uma única STRING que deve registrar um logradouro pode ter o tamanho 30 em um exemplo e 50 em outro. Se inclui uma lista, pode ter 0 elementos em um caso e 20 em outro.  

#### Representação (serialização) de um registro
Um registro é formado por um _header_ seguido dos dados correspondentes aos 
tipos dos campos do registro, conforme ilustrado abaixo.

```
+-----------------+
|      RECORD     |
+-----------------+
| Header | Fields |
+-----------------+
``` 

##### Representação dos dados de um registro
Abaixo segue a ilustração de um registro, sem o detalhamento do _header_. Esse registro reúne um campo inteiro e duas sequências de caracteres. Ou seja, os campos são dos tipos INT, STRING e STRING. O valor do INT é 23 (faz uso de 4 bytes); a primeira STRING apenas
4 bytes ("nome") e a segunda STRING ocupa outros 7 bytes ("contato"). A primeira linha contendo números abaixo indica os 
deslocamentos do início de cada campo com base na posição inicial (0). Ou seja, o inteiro faz 
uso dos bytes de 0 a 3 (inclusive), o "nome" ocupa os quatro bytes seguintes de 4 a 7 (inclusive) e, 
por último, "contato" faz uso dos bytes de 8 a 14 (inclusive). O décimo quinto byte está além do registro. 
Nesse arranjo observe que o tipo de tamanho fixo (INT) segue antes dos demais, ou seja, aquele de tamanho fixo segue antes daqueles de tamanho variável.

```
+------------------------------+
| HEADER |       FIELDS        |
+------------------------------+
---------|0---|4-----|8--------|15
+------------------------------+
| HEADER | 23 | nome | contato |
+------------------------------+
```

##### Representação do header de um registro
O _header_ obrigatoriamente identifica, em seu primeiro byte, o tipo do registro. Observe que não é o formato propriamente dito, mas um identificador que permite localizar o formato empregado pelo registro. Dado que um único byte é empregado, tem-se um limite natural para os possíveis formatos (tipos) de registros. 

Em geral o tipo de um registro inclui campos de tamanho variável. Nesses caso, o _header_ deve 
conter várias informações:
- *Tipo*, que identifica unicamente o formato do registro.
- *Tamanho do registro*. Permite rapidamente "saltar" para o próximo registro. Observe que esse valor pode
ser "recuperado" a partir do percurso do conteúdo do registro. 
- *Apontadores*. Após campos de tamanho fixo, que não dependem de apontadores, segue o primeiro campo de tamanho variável que também não depende de apontador. Contudo, após o primeiro campo de tamanho variável, todos os demais dependem de "saltar" sobre o conteúdo dos dados para serem localizados ou de apontadores, que não dependem desse percurso. Imagine por exemplo uma STRING. Segundo o tipo de registro ilustrado anteriormente, para o acesso à segunda STRING estão disponíveis duas estratégias: (a) localiza-se o término da STRING anterior (sabe-se que a seguinte é iniciada no byte seguinte) e (b) um apontador no _header_ pode indicar diretamente o início da segunda STRING.

Abaixo segue ilustração do registro exemplo apresentado anteriormente, agora acrescida do _header_. Suponha que o tipo de valor 54
identifica unicamente o formato desse registro, ou seja, a sequência formada por um INT, uma STRING e outra STRING.

```
+-----------------------------------+
|   HEADER    |      FIELDS         |
+-----------------------------------+
--------------|0---|4-----|8--------|15
+-----------------------------------+
| 54 | 15 | 8 | 23 | nome | contato |
+-----------------------------------+
```

Interpretação de cada um dos valores acima:
- Primeiro segue o tipo do registro, valor 54 (suposição estabelecida acima). 
- O tipo é seguido do tamanho do registro, 15 bytes de dados. Esse tamanho não é o
tamanho total do registro, pois não inclui os bytes empregados pelo _header_, mas apenas aqueles que dizem respeito aos dados propriamente ditos (ou _payload_).
- O último valor do _header_ é 8, a posição inicial do campo "contato". Observe que ao
manter os campos de tamanho fixo no início, em ordem bem definida, não é necessário
indicar a posição deles, nem do primeiro de tamanho variável, nesse caso "nome". Ou seja,
para um registro do tipo 54 é suficiente armazenar a posição de início do último campo,
posição 8.

> Decisões
> * Campos de tamanho fixo precedem todos os campos de tamanho variável.
> * Valor de posição no header é relativa à posição inicial (0) dos dados, imediatamente após o _header_.

O registro representado na ilustração acima fornece o comportamento geral. 
Contudo, há situações especiais que demandam alteração na representação tanto
do _header_ quanto do dados. Contudo, isso é melhor compreendido após a introdução
de outras questões: (a) blocos e (b) fragmentação de registros.  

#### Blocos (elemento de divisão de um arquivo)
Uma base de dados é armazenada em um arquivo didivido em blocos de tamanho fixo. O tamanho padrão é 4KB. O acesso ao conteúdo da base de dados significa que esses blocos precisam ser transferidos para a memória RAM. No sentido inverso, atualizações precisam ser depositadas no bloco correspondente no arquivo em questão.

#### Fragmentação de registro
Dado que apenas parte da informação de uma base de dados se encontra em RAM, ou seja, apenas alguns blocos, e que um bloco possui tamanho fixo, enquanto os registros não, é natural que a divisão em blocos "fragmente" um registro no sentido em que parte das informações podem estar no final de um bloco e as demais a partir do bloco seguinte. De fato, um registro pode estar "espalhado" por vários blocos. Em particular, um único campo pode estar espalhado por vários blocos. 

> Decisão
> * Dados de um registro podem estar espalhados por vários blocos contíguos.

Abaixo é ilustrado o cenário onde o registro está disposto em dois blocos, sem perda de generalidade, assuma que são os 
blocos 6 e 7. Nessa ilustração, a STRING "contato" é
dividida em "cont" (bloco 6) e "ato" (bloco 7). 

```
------------ Bloco 6 -----------||----------- Bloco 7 ------------
--------------|0---|4-----|8----||----|15
+-------------------------------------+
| 54 | 15 | 8 | 23 | nome | cont||ato |
+-------------------------------------+
```

A estratégia acima tem como ponto positivo maximizar o uso de cada byte de um bloco.
Em consequência, por outro lado, introduz possível "área" não preenchida ao final de um bloco,
quando o espaço disponível não for suficiente para registrar todo o _header_ do registro seguinte, por exemplo,
conforme ilustrado abaixo, um marcador indica que o registro em questão continua no bloco seguinte.

```
------------ Bloco 6 ---------------------------||------ Bloco 7 ------------
--------------|0---|4-----|8----------|15-------|17
+-------------------------------------+---------||
| 54 | 15 | 8 | 23 | nome | contato   |@        ||<Header do próximo registro>
+-------------------------------------+---------||
```

#### Endereços
Um apontador indica o início de um campo relativo ao início dos dados do registro em questão. 
Ou seja, esse endereço não é o endereço do byte correspondente no arquivo onde é armazenado, também não coincide com o deslocamento referente ao bloco no qual se encontra. 

