-- ********************************** CREATE DATABASE **********************************
drop database if exists shoe_world_webshop_db;
create database shoe_world_webshop_db;
use shoe_world_webshop_db;
set sql_safe_updates = 0;
-- set autocommit = 0;

-- ********************************** CREATE TABLES ************************************
create table if not exists sizes
(id int not null auto_increment,
size int not null unique,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key (id));

create table if not exists brands
(id int not null auto_increment,
brand varchar(20) unique,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key (id));

create table if not exists grades
(id int not null auto_increment,
grade varchar(20) unique,
points int not null unique,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key (id));

create table if not exists categories
(id int not null auto_increment,
category varchar(20) unique,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key(id));

create table if not exists customers
(membership_nr int not null auto_increment,
first_name varchar(20) not null,
last_name varchar(20) not null,
street_address varchar(30) not null, 
zip_code varchar(10) not null, 
city varchar(20) not null,
password varchar(20) not null,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key (membership_nr));

create table if not exists shoes
(article_nr int not null, 
brand_id int, 
item_name varchar(20) not null,
color varchar(10),
price_sek int not null, 
size_id int,
in_stock int default 0, -- Tillagt lagersaldo
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key (article_nr),
foreign key (brand_id) references brands(id) 
	on delete set null on update cascade,
foreign key (size_id) references sizes(id)
	on delete set null on update cascade);
		-- Vi vill att en sko ska finnas kvar även om
		-- märket och storleken skulle tas bort eller uppdateras

create table if not exists belongs_to_category
(id int not null auto_increment,
shoe_article_nr int not null, 
category_id int,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key(id),
foreign key (shoe_article_nr) references shoes(article_nr)
	on delete cascade on update cascade,
foreign key (category_id) references categories(id)
	on delete set null on update cascade);
		-- Vi vill ta bort de rader som ej refererar till en sko men 
		-- behålla raderna som endast pekar på en sko (ej category)

create table if not exists orders
(order_nr int not null auto_increment, 
customer_id int,
order_date date not null,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key(order_nr),
foreign key (customer_id) references customers(membership_nr)
	on delete set null on update cascade);
		-- Även om en kund skulle ta bort sin profil så ska ordern finnas kvar

create table if not exists order_rows
(id int not null auto_increment,
order_nr int not null,
shoe_article_nr int not null,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key (id),
foreign key(order_nr) references orders(order_nr)
	on delete cascade on update cascade,
foreign key(shoe_article_nr) references shoes(article_nr)
	on delete cascade on update cascade);
		-- Om hela ordern tas bort ska även varje order rad tas bort samt 
		-- om en sko försvinner ur sortimentet så ska den inte kunna beställas

create table if not exists reviews
(id int not null auto_increment,
review_text varchar(512),
shoe_article_nr int not null,
customer_id int,
grade_id int,
created timestamp default current_timestamp,
lastUpdate timestamp default Current_Timestamp on update current_timestamp,
primary key(id),
foreign key(shoe_article_nr) references shoes(article_nr)
	on delete cascade on update cascade,
foreign key(customer_id) references customers(membership_nr)
	on delete set null on update cascade,
foreign key(grade_id) references grades(id)
	on delete set null on update cascade);
		-- Vi vill att en recension ska finnas kvar även om 
		-- kundens profil eller betygsskalan tas bort. 

create table if not exists out_of_stock
(id int not null auto_increment,
shoe_article_nr int, 
sold_out_on datetime not null default now(),
primary key (id),
foreign key (shoe_article_nr) references shoes(article_nr));

create index IX_price on shoes(price_sek);
create index IX_item on shoes(item_name);


-- ********************************** CREATE FUNCTIONS ***************************************
-- Skapa en funktion som tar ett produktId som parameter och returnerar medelbetyget för den produkten. 
-- Om du inte har sifferbetyg sedan innan, lägg till dessa, 
-- så att en siffra motsvarar ett av de skriftliga betygsvärdena.

DROP FUNCTION IF EXISTS getAverageScoreOfShoe;
Delimiter //
CREATE FUNCTION getAverageScoreOfShoe(articleNr int) 
	RETURNS double
    READS SQL DATA
BEGIN
	RETURN
    (select average_score
    from average_grades
    where article_nr = articleNr);
END //
Delimiter ;

DROP FUNCTION IF EXISTS getAverageGrade;
Delimiter // 
CREATE FUNCTION getAverageGrade(score DOUBLE)
	RETURNS VARCHAR(20)
    READS SQL DATA
BEGIN
	CASE
		WHEN score <= 1.5 THEN return 'Missnöjd';
        WHEN score <= 2.5 THEN return 'Ganska Nöjd';
        WHEN score <= 3.5 THEN return 'Nöjd';
        WHEN score > 3.5 THEN return 'Mycket Nöjd';
        ELSE return null;
	END CASE;
END //
Delimiter ;

-- Hämta det senate order numret

DROP FUNCTION IF EXISTS getLatestOrderNr;
Delimiter //
CREATE FUNCTION getLatestOrderNr() 
	RETURNS int
    READS SQL DATA
BEGIN
RETURN 
    (select max(o.order_nr)
    from orders o);
END //
Delimiter ;

DROP FUNCTION IF EXISTS isPasswordCorrect;
Delimiter //
CREATE FUNCTION isPasswordCorrect(_membership_nr int, _password varchar(20))
	RETURNS BOOL
    READS SQL DATA
Begin
	declare amount int default 0;
	SELECT count(*) FROM customers WHERE membership_nr = _membership_nr AND password = _password into amount;
    IF amount = 1 Then return true;
    ELSE return false;
    END if;
END //
Delimiter ;

DROP FUNCTION IF EXISTS doesShoeExist;
Delimiter //
CREATE FUNCTION doesShoeExist(articleNr int)
	RETURNS BOOL
    READS SQL DATA
begin
	declare amount int default 0;
    SELECT count(*) FROM shoes WHERE article_nr = articleNr into amount;
    IF amount = 1 Then return true;
    ELSE return false;
    END if;
end //
Delimiter ;


-- ********************************** CREATE VIEWS *****************************************
-- Skapa en vy som visar medelbetyget i siffror och i text för samtliga produkter 
-- (om en produkt inte har fått något betyg så skall den ändå visas, 
-- med null eller tomt värde, i medelbetyg).

DROP VIEW IF EXISTS average_grades;
create or replace view average_grades as 
Select s.article_nr, b.brand, s.item_name, s.color, si.size, round(avg(g.points),1) as 'average_score', getAverageGrade(avg(g.points)) as 'average_grade'
from shoes s 
join brands b 
	on b.id = s.brand_id
join sizes si
	on si.id = s.size_id
left join reviews r 
	on r.shoe_article_nr = s.article_nr
left join grades g 
	on g.id = r.grade_id
group by s.article_nr 
order by average_score desc, s.article_nr;

-- Vy som visar alla reviews som finns med grade text

DROP VIEW IF EXISTS shoe_reviews;
create or replace view shoe_reviews as
select shoe_article_nr, grade, review_text, points, c.first_name, c.last_name
from reviews r 
join grades g on g.id = r.grade_id
join customers c on c.membership_nr = r.customer_id
order by points desc;

-- Vy som visar hur många gånger en produkt har blivit slutsåld

DROP VIEW IF EXISTS times_sold_out;
create or replace view times_sold_out as
select s.article_nr, s.item_name, b.brand, si.size, s.color, count(*) as 'times_sold_out'
from out_of_stock oos
join shoes s on s.article_nr = oos.shoe_article_nr
join sizes si on si.id = s.size_id
join brands b on b.id = s.brand_id
group by oos.shoe_article_nr
order by times_sold_out desc;

-- Vy som visar vilka skor som har 0 eller mindre i lagersaldo

DROP VIEW IF EXISTS backlog;
create or replace view backlog as
select s.article_nr, s.item_name, b.brand, si.size, s.color, s.in_stock as 'available'
from shoes s
join sizes si on si.id = s.size_id
join brands b on b.id = s.brand_id
where s.in_stock <= 0
order by available;

-- Vy som visar alla skor med info

DROP VIEW IF EXISTS shoe_search;
create or replace view shoe_search as
select article_nr, b.brand, item_name, color, si.size, in_stock
from shoes s 
join brands b on b.id = s.brand_id
join sizes si on si.id = s.size_id
order by article_nr;

-- Vy som visar alla kategorier med tillhörande skor

DROP VIEW IF EXISTS category_search;
create or replace view category_search as
select c.category, s.article_nr, b.brand, s.item_name, s.color, si.size, s.in_stock
from shoes s
join brands b on b.id = s.brand_id
join sizes si on si.id = s.size_id
join belongs_to_category bts on bts.shoe_article_nr = s.article_nr
join categories c on c.id = bts.category_id
order by c.category;

-- Skriv ut alla skor i en viss order

DROP VIEW IF EXISTS show_client_order;
create or replace view show_client_order as
select o.order_nr as orderNr, s.article_nr, s.item_name, b.brand, s.color, si.size, o.order_date
from orders o 
join order_rows orow on orow.order_nr = o.order_nr
join shoes s on s.article_nr = orow.shoe_article_nr
join brands b on b.id = s.brand_id
join sizes si on si.id = s.size_id;

-- ********************************** CREATE PROCEDURE ****************************************
-- Skapa en stored procedure ”AddToCart”. Denna procedur skall ta kundid, beställningsid och produktid som inparametrar. 
-- (Beroende på hur din implementation ser ut kan du behöva använda andra värden som inparametrar, 
-- justera i så fall parameterlistan för att kunna peka ut en produkt, kund och beställning.) AddToCart ska uppfylla följande:
-- • Om beställningen inte finns eller om vi skickar in null som beställningsid ska en ny beställning skapas och produkten läggas till i den.
-- • Om beställningen redan finns ska produkten läggas till i beställningen.
-- • Om beställningen finns och produkten redan finns i den ska vi lägga till ytterligare ett exemplar av produkten i beställningen.
-- • För varje produkt som blir tillagd i en beställning ska lagerantalet av produkten minska.
-- • Använd dig av transaktioner och felhantering

DROP PROCEDURE IF EXISTS addToCart;
Delimiter //
CREATE PROCEDURE addToCart(customerID int, shoeID int, orderID int)
	MODIFIES SQL DATA
BEGIN
	declare exit handler for sqlexception
    begin
		rollback;
        RESIGNAL SET MESSAGE_TEXT  = 'SQL Exception - Could not att to cart.';
    end;

	start transaction;
	IF (orderID < 1)
		THEN 
        insert into orders(customer_id, order_date) values (customerID, date_format(now(),'%Y-%m-%d'));
		insert into order_rows(order_nr, shoe_article_nr) values ((select max(order_nr) from orders), shoeID);
		update shoes set in_stock = in_stock - 1 where article_nr = shoeID;
    ELSE 
		insert into order_rows(order_nr, shoe_article_nr) values (orderID, shoeID);
        update shoes set in_stock = in_stock - 1 where article_nr = shoeID;
    END IF;
    commit;
END //
Delimiter ;

-- Skapa en stored procedure ”Rate” som lägger till ett betyg och 
-- en kommentar på en specifik produkt för en specifik kund.

DROP PROCEDURE IF EXISTS rate;
Delimiter //
CREATE PROCEDURE rate(text varchar(512), shoe int, customer int, rate int)
    MODIFIES SQL DATA
begin
	declare exit handler for sqlexception
	begin
		rollback;
        RESIGNAL SET MESSAGE_TEXT  = 'SQL Exception - Could not submit review.';
    end;
	
	start transaction;
	insert into reviews(review_text, shoe_article_nr, customer_id, grade_id) values
    (text, shoe, customer, rate);
    commit;
end //
Delimiter ;

-- En procedure som lägger till x antal i saldo

DROP PROCEDURE IF EXISTS addToInventory;
Delimiter // 
CREATE PROCEDURE addToInventory(articleNr int, amount int)
	MODIFIES SQL DATA
BEGIN
	declare exit handler for sqlexception
	begin
		rollback;
        RESIGNAL SET MESSAGE_TEXT  = 'SQL Exception - Could not add to inventory.';
    end;
    
	start transaction;
	update shoes set in_stock = in_stock + amount where article_nr = articleNr;
    commit;
END //
Delimiter ;

-- ********************************** CREATE TRIGGERS **************************************
-- Vi vill även kunna kolla upp hur ofta en viss produkt säljer slut. 
-- Skapa därför en trigger som lägger in en rad i en tabell som heter Slutilager om en produkt tar slut i lager. 
-- Slutilager skall innehålla datum och produktId.

DROP TRIGGER IF EXISTS after_update_shoes;
Delimiter // 
create trigger after_update_shoes
	after update
    on shoes for each row
begin
	IF(new.in_stock = 0)
    THEN 
    insert into out_of_stock(shoe_article_nr) values(old.article_nr);
    END IF;
end //
Delimiter ;

-- ********************************** INSERT DATA ******************************************

insert into sizes (size) values 
(38),(40),(42);

insert into brands (brand) values 
('Ecco'),('Nike'),('Adidas'),('Puma');

insert into grades (grade,points) values 
('Mycket Nöjd',4),
('Nöjd',3),
('Ganska Nöjd',2),
('Missnöjd',1);

insert into categories (category) values 
('Indoor'),('Outdoor'),('Walking'),
('Hiking'),('Running'),('Sandal');

insert into customers (first_name, last_name, street_address, zip_code, city, password) values
('Gustav','Svensson','Sverigegatan 2','12335','Stockholm','1234'),
('Marie','Lundberg','Långa gatan 5','21345','Göteborg','1234'),
('Bertil','Bengtsson','Korta gatan 78','12313','Malmö','1234'),
('Ture','Sten','Vattenvägen 45','52424','Alingsås','1234'),
('Arne','Weise','Kanalvägen 1','45353','Stockholm','1234'),
('Berit','Stig','Kanalvägen 1','45353','Stockholm','1234'),
('Håkan','Loob','Mellanstigen 56','43532','Kristinehamn','1234'),
('Lotta','Engberg','Låtsasvägen 56','45646','Göteborg','1234'),
('Anders','Sten','Mellangården 75','45646','Malmö','1234');

insert into shoes (article_nr, brand_id, item_name, color, price_sek, size_id, in_stock) values
(2021038,1,'Ice Warrior','Black',799,1,5),
(2021040,1,'Ice Warrior','Black',799,2,5),
(2021042,1,'Ice Warrior','Black',799,3,5),
(2021138,1,'Ice Warrior','White',799,1,5),
(2021140,1,'Ice Warrior','White',799,2,5),
(2021142,1,'Ice Warrior','White',799,3,5),
(2021238,2,'Dry Walker','Beige',699,1,5),
(2021240,2,'Dry Walker','Beige',699,2,5),
(2021242,2,'Dry Walker','Beige',699,3,5),
(2021338,2,'Dry Walker','White',699,1,5),
(2021340,2,'Dry Walker','White',699,2,5),
(2021342,2,'Dry Walker','White',699,3,5),
(2021438,4,'Runner Haze','Brown',499,1,5),
(2021440,4,'Runner Haze','Brown',499,2,5),
(2021442,4,'Runner Haze','Brown',499,3,5),
(2021538,4,'Runner Haze','Gray',499,1,5),
(2021540,4,'Runner Haze','Gray',499,2,5),
(2021542,4,'Runner Haze','Gray',499,3,5),
(2021638,3,'Lofer X','Black',899,1,5),
(2021640,3,'Lofer X','Black',899,2,5),
(2021642,3,'Lofer X','Black',899,3,5),
(2021738,1,'Beach Slipp','Black',299,1,5),
(2021740,1,'Beach Slipp','Black',299,2,5),
(2021742,1,'Beach Slipp','Black',299,3,5),
(2021838,2,'Max Power','Black',999,1,5),
(2021840,2,'Max Power','Black',999,2,5),
(2021842,2,'Max Power','Black',999,3,5),
(2021938,2,'Max Power','Gray',999,1,5),
(2021940,2,'Max Power','Gray',999,2,5),
(2021942,2,'Max Power','Gray',999,3,5),
(2022038,2,'Max Power','White',999,1,5),
(2022040,2,'Max Power','White',999,2,5),
(2022042,2,'Max Power','White',999,3,5);

insert into belongs_to_category (shoe_article_nr, category_id) values
(2021038,2),
(2021038,4),
(2021038,5),
(2021040,2),
(2021040,4),
(2021040,5),
(2021042,2),
(2021042,4),
(2021042,5),
(2021138,2),
(2021138,4),
(2021138,5),
(2021140,2),
(2021140,4),
(2021140,5),
(2021142,2),
(2021142,4),
(2021142,5),
(2021238,2),
(2021238,3),
(2021238,4),
(2021238,5),
(2021240,2),
(2021240,3),
(2021240,4),
(2021240,5),
(2021242,2),
(2021242,3),
(2021242,4),
(2021242,5),
(2021338,2),
(2021338,3),
(2021338,4),
(2021338,5),
(2021340,2),
(2021340,3),
(2021340,4),
(2021340,5),
(2021342,2),
(2021342,3),
(2021342,4),
(2021342,5),
(2021438,1),
(2021438,4),
(2021440,1),
(2021440,4),
(2021442,1),
(2021442,4),
(2021538,1),
(2021538,4),
(2021540,1),
(2021540,4),
(2021542,1),
(2021542,4),
(2021638,6),
(2021640,6),
(2021642,6),
(2021738,6),
(2021740,6),
(2021742,6),
(2021838,1),
(2021838,4),
(2021838,5),
(2021840,1),
(2021840,4),
(2021840,5),
(2021842,1),
(2021842,4),
(2021842,5),
(2021938,1),
(2021938,4),
(2021938,5),
(2021940,1),
(2021940,4),
(2021940,5),
(2021942,1),
(2021942,4),
(2021942,5),
(2022038,1),
(2022038,4),
(2022038,5),
(2022040,1),
(2022040,4),
(2022040,5),
(2022042,1),
(2022042,4),
(2022042,5);
