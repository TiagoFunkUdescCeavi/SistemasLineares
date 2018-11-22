dados <- read_excel("~/Repositorios/Github/SistemasLineares/R/teste.xlsx")

tempo <- dados$tempo
dinheiro <- dados$dinheiro
idade <- dados$idade

legendaBoxPlot <- c("tempo", "dinheiro", "idade")

boxplot(tempo, dinheiro, idade, main="Tempo de execução do cálculo do algoritmo", 
        names=legendaBoxPlot, ylab="Tempo (ms)", xlab="sei la")

resulAnova <- aov(tempo ~ dinheiro + idade)

# Mostra a tabela ANOVA
summary(resulAnova)

#TukeyHSD(resulAnova)
