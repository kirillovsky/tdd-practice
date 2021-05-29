#TDD practice

| **Instrument** | **Shares** | **Price** | **Total** |
|----------------|------------|-----------|-----------|
| IBM            | 1000       | 25 USD    | 25000 USD |
| Novartis       | 400        | 150 CHF   | 60000 USD |
|                |            | **Total** | 65000 USD |

Задание: необходимо реализовать доменную модель для отображение заданого отчета.

Примеры использования:
```
1 USD = 1 USD
1 USD != 2 USD
1 USD != 1 CFH
2 USB * 2 = 4 USD
2 CHF + 4 USD = 5 USD (if rate 4:1)
2 CHF + 4 USD = 10 CHF (if rate 2:1)
2 EUR + 4 USD = 8 USD
no negative amount
to big amount (more than int)
```
**Задание на дом**
1. Доделать Wallet, чтобы он мог использоваться с несколькими "стопками денег"
2. Привнести EUR в проект, при том что 3 валюты могли конвертироваться друг в друга
3. (задание со звездочкой) Сделать так, чтобы 
   `Waller(dollar(30), franc(20)) + franc(20)` работало