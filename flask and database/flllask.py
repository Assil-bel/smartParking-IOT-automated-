from flask import Flask,request
#from .controller import *
from flask_api import status
import json
import psycopg2




#
def retrieveAllDB():
    con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
    cur = con.cursor()
    cur.execute("SELECT * from Parking ORDER BY spot")
    spotRows = cur.fetchall()
    print(spotRows)
    print("Operation done successfully")
    con.close()
    return spotRows

#


def setDb(spot):
    con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
    cur = con.cursor()
    cur.execute(f"UPDATE Parking set STATE= '1' where SPOT={spot}")
    con.commit()
    print("SPOT RESERVED SUCCESSFULLY:", cur.rowcount)
    print("Operation done successfully")
    con.close()

#
def resetDb(spot):
    con = psycopg2.connect(database="allsetdb", user="postgres", password="1234", host="127.0.0.1", port="5432")
    cur = con.cursor()
    cur.execute(f"UPDATE Parking set STATE= '0' where SPOT={spot}")
    con.commit()
    print("SPOT LEFT:", cur.rowcount)
    print("Operation done successfully")
    con.close()


app = Flask(__name__)


spots = retrieveAllDB()
SPOTLIST = []

for i in spots:
    obj={'spot':i[0],'instruct':i[1],'state':i[2]}
    SPOTLIST.append(obj)
jsonSPOTS = json.dumps(SPOTLIST)
print(jsonSPOTS)



@app.route('/')
def index():
    spots = retrieveAllDB()
    SPOTLIST = []

    for i in spots:
        obj = {'spot': i[0], 'instruct': i[1], 'state': i[2]}
        SPOTLIST.append(obj)
    jsonSPOTS = json.dumps(SPOTLIST)
    return jsonSPOTS

@app.route('/json-example',methods=['GET', 'POST'])
def json_example():
    if request.method == 'POST':
        choosenSPOT = request.get_json(request.data)
        print(choosenSPOT["spot"])
        if choosenSPOT["status"]=='1':
            setDb(choosenSPOT["spot"])
        else:
            resetDb(choosenSPOT["spot"])
        return '{"state":"succes"}'
    return ''


if __name__ == "__main__":

   app.debug = True
 #  try:
   app.run(host="192.168.43.88", port=8000)
 #  except KeyboardInterrupt:
      # con.close()










   # class Spot:
   #      def __init__(self,spot, instruction,state):
   #           self.spot =spot
   #           self.instruction=instruction
   #           self.state=state
   #      def __str__(self):
   #           return str(self.__class__)+": "+str(self.__dict__)
   #
   # for row in spots:
   #     SPOTLIST.append(Spot(row[0], row[1], row[2]).__dict__)







