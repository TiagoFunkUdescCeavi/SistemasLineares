dados_anova <- read_excel("Repositorios/Github/SistemasLineares/R/dados_anova.xlsx")

resulAnova <- aov(dados_anova$leitura ~ dados_anova$linguagem + dados_anova$ordem )

# Mostra a tabela ANOVA
summary(resulAnova)

#                          Df Sum Sq Mean Sq F value Pr(>F)    
# dados_anova$linguagem   2 137901   68950   526.4 <2e-16 ***
# dados_anova$ordem       1 146778  146778  1120.6 <2e-16 ***
#   Residuals             176  23052     131                   
# ---
#   Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

TukeyHSD(resulAnova)
# Tukey multiple comparisons of means
# 95% family-wise confidence level
# 
# Fit: aov(formula = dados_anova$leitura ~ dados_anova$linguagem + dados_anova$ordem)
# 
# $`dados_anova$linguagem`
#               diff        lwr       upr      p adj
# Java-Cpp     63.33452  58.395561  68.27347 0.0e+00
# Python-Cpp   10.71322   5.774267  15.65218 2.3e-06
# Python-Java -52.62129 -57.560250 -47.68234 0.0e+00

resulAnova <- aov(dados_anova$calculo ~ dados_anova$linguagem + dados_anova$ordem )
summary(resulAnova)
#                         Df    Sum Sq   Mean Sq F value Pr(>F)    
#   dados_anova$linguagem   2 265609352 132804676   99.52 <2e-16 ***
#   dados_anova$ordem       1 115244229 115244229   86.36 <2e-16 ***
#   Residuals             176 234863038   1334449                   
# ---
#   Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

TukeyHSD(resulAnova)
# Tukey multiple comparisons of means
# 95% family-wise confidence level
# 
# Fit: aov(formula = dados_anova$calculo ~ dados_anova$linguagem + dados_anova$ordem)
# 
# $`dados_anova$linguagem`
#                   diff       lwr       upr     p adj
# Java-Cpp      -8.860583 -507.3833  489.6621 0.9990274
# Python-Cpp  2572.423395 2073.9007 3070.9461 0.0000000
# Python-Java 2581.283978 2082.7613 3079.8066 0.0000000
# 
# Warning messages:
#   1: In replications(paste("~", xx), data = mf) :
#   non-factors ignored: dados_anova$ordem
# 2: In TukeyHSD.aov(resulAnova) :
#   'which' especifiou alguns não-fatores que serão descartados