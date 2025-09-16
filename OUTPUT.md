![img_26.png](img_26.png)

=========================================

1) POST /auth/login

![img.png](img.png)
![img_1.png](img_1.png)

==========================================

2) GET /resources — list (paginated)
![img_2.png](img_2.png)
![img_3.png](img_3.png)

3) GET /resources/{id} — details
 ![img_4.png](img_4.png)
![img_5.png](img_5.png)

4)POST /resources — ADMIN only

    Role ====> "ADMIN"
![img_6.png](img_6.png)
![img_7.png](img_7.png)

    Role ====> "USER"
![img_8.png](img_8.png)
![img_9.png](img_9.png)

5) PUT /resources/{id} — ADMIN only

    Role ====> "ADMIN"

    Valid Id
![img_10.png](img_10.png)
![img_11.png](img_11.png)

Invalid Id
![img_13.png](img_13.png)
![img_12.png](img_12.png)

    Role ====> "USER"
![img_20.png](img_20.png)
![img_21.png](img_21.png)

6) DELETE /resources/{id} — ADMIN only

    Role ====> "ADMIN"

    Invalid Id
![img_14.png](img_14.png)
![img_15.png](img_15.png)

Valid ID

![img_16.png](img_16.png)
![img_17.png](img_17.png)

    Role ====> "USER"

![img_18.png](img_18.png)
![img_19.png](img_19.png)

==========================================

RESERVATIONS

7)POST /reservations

![img_22.png](img_22.png)
![img_23.png](img_23.png)

8) GET /reservations

   ADMIN: list all

![img_24.png](img_24.png)
![img_25.png](img_25.png)

   USER: list own only










