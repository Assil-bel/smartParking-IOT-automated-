import psycopg2


con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
print("Database opened successfully")

def createDB():

    cur = con.cursor()
    cur.execute('''CREATE TABLE Parking
          (SPOT           INT PRIMARY KEY    NOT NULL,
          INSTRUCTION    CHAR(50),
          STATE       BIT   NOT NULL);''')
    print("Table created successfully")

    con.commit()
    con.close()

def insertDB():
    cur = con.cursor()

    for i in range(1,10):
        cur.execute(
            f"INSERT INTO Parking (SPOT,INSTRUCTION,STATE) VALUES ({i}, 'not YET','0')")

    con.commit()
    print("Records inserted successfully")
    con.close()

def retrieveAllDB():

    cur = con.cursor()
    cur.execute("SELECT * from Parking ORDER BY spot")
    spotRows = cur.fetchall()
    print(spotRows)
    print("Operation done successfully")
    return spotRows

def retrieveOneDB(spot):
    con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
    cur = con.cursor()
    cur.execute(f"SELECT * from Parking where SPOT={spot}")
    spotRow = cur.fetchone()
    currentState =spotRow[2]
    print(spotRow[2])
    print("Operation done successfully")
    con.close()
    return currentState

def updateDb(spot):
    con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
    currentState=retrieveOneDB(spot)
    if currentState == '0':
        con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
        cur = con.cursor()
        cur.execute(f"UPDATE Parking set STATE= '1' where SPOT={spot}")
        con.commit()
        print("SPOT RESERVED SUCCESSFULLY:", cur.rowcount)
    else:
        con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
        cur = con.cursor()
        cur.execute(f"UPDATE Parking set STATE= '0' where SPOT={spot}")
        con.commit()
        print("SPOT LEFT:", cur.rowcount)

    print("Operation done successfully")
    con.close()

def setDb(spot):
    cur = con.cursor()
    cur.execute(f"UPDATE Parking set STATE= '1' where SPOT={spot}")
    con.commit()
    print("SPOT RESERVED SUCCESSFULLY:", cur.rowcount)
    print("Operation done successfully")

def resetDb(spot):
    cur = con.cursor()
    cur.execute(f"UPDATE Parking set STATE= '0' where SPOT={spot}")
    con.commit()
    print("SPOT LEFT:", cur.rowcount)
    print("Operation done successfully")

def retriedEmptyDB():
    all=retrieveAllDB()
    empty_spaces=[]
    for spot in all:
        if spot[2]=='0':
            empty_spaces.append(spot[0])
    print(f"choose from the following available spots: {empty_spaces}")

