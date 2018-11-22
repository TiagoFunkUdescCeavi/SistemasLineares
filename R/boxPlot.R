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



legendaBoxPlot <- c("100 C++", "100 Java","100 Python",
                    "200 C++", "200 Java","200 Python",
                    "300 C++", "300 Java","300 Python")

par(mar=c(7,4,2,2))
boxplot(
  l100cpp, l100java, l100python,
  l200cpp, l200java, l200python,
  l300cpp, l300java, l300python,
  main="Tempo de leitura (ms).",names=legendaBoxPlot, 
  ylab="Tempo (ms)", xlab="Ordem sistema", las=2,
  col = c("white", "grey", "lightblue", "white", "grey", "lightblue","white", "grey", "lightblue" )
)
legend(
       "top",
       inset = .02,
       c("C++", "Java", "Python"),
       horiz=T,
       fill=c("white", "grey", "lightblue")
)

resulAnova <- aov(l100cpp ~ l100java + l100python)

# Mostra a tabela ANOVA
summary(resulAnova)

TukeyHSD(resulAnova)
