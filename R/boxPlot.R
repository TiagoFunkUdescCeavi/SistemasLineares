dados <- read_excel("Repositorios/Github/SistemasLineares/R/dados.xlsx")

l100cpp <- dados$l100cpp
l100java <- dados$l100java
l100python <- dados$l100python

l200cpp <- dados$l200cpp
l200java <- dados$l200java
l200python <- dados$l200python

l300cpp <- dados$l300cpp
l300java <- dados$l300java
l300python <- dados$l300python



legendaBoxPlot <- c("Ordem 100-C++", "Ordem 100-Java","Ordem 100-Python",
                    "Ordem 200-C++", "Ordem 200-Java","Ordem 200-Python",
                    "Ordem 300-C++", "Ordem 300-Java","Ordem 300-Python")

par(mar=c(9,5,2,2))
boxplot(
  l100cpp, l100java, l100python,
  l200cpp, l200java, l200python,
  l300cpp, l300java, l300python,
  main="Tempo de leitura (ms).",names=legendaBoxPlot, 
  ylab="Tempo (ms)", las=2
  #col = c("white", "grey", "lightblue", "white", "grey", "lightblue","white", "grey", "lightblue" )
)

c100cpp <- dados$c100cpp
c100java <- dados$c100java
c100python <- dados$c100python

c200cpp <- dados$c200cpp
c200java <- dados$c200java
c200python <- dados$c200python

c300cpp <- dados$c300cpp
c300java <- dados$c300java
c300python <- dados$c300python

par(mar=c(9,5,2,2))
boxplot(
  c100cpp, c100java, c100python,
  c200cpp, c200java, c200python,
  c300cpp, c300java, c300python,
  main="Tempo de cálculo (ms).",names=legendaBoxPlot, 
  ylab="Tempo (ms)", las=2
)

