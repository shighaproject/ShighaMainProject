from typing import re

from flask import  *
import MySQLdb
import os
from werkzeug.utils import secure_filename
app=Flask(__name__)
app.static_folder="static"
path="F:\\project\\Third_Eye\\static\\employee_pic"
path1="F:\project\Third_Eye\static\employee_work"
con=MySQLdb.connect(host='localhost',user='root',passwd='',port=3306,db='project')
cmd=con.cursor()


@app.route('/log',methods=['POST','GET'])
def log():
    username = request.args.get('username')
    password= request.args.get('password')
    print(username)
    print(password)
    cmd.execute("select * from login_tb where username='" + str(username) + "' and password='" + str(password) + "'")
    s = cmd.fetchone()
    if (s is None):
        return jsonify({'task': "invalid"})
    else:
        return jsonify({'task': str(s[0])})



@app.route('/tl_view_employee',methods=['POST','GET'])
def tl_view_employee():
    try:
        lid = request.args.get('lid')
        cmd.execute("select * from h_add_tb where hid='"+str(lid)+"'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
       print(str(e))
       return jsonify({'result': "na"})

@app.route('/delete_profile',methods=['POST','GET'])
def delete_profile():
    lid = request.args.get('id')

    cmd.execute("delete from h_add_tb where eid=" + str(lid) + "")
    con.commit()
    return jsonify({'task': "deleted"})


@app.route('/tl_register',methods=['POST','GET'])
def tl_register():
    em_type=request.form['em_type']
    hid = request.form['headid']
    name = request.form['name']
    place = request.form['place']
    dob = request.form['dob']
    gender = request.form['gender']
    mobile = request.form['mobile']
    email =request.form['email']
    qualification = request.form['qualification']
    experience = request.form['experience']
    photo = request.files['files']
    img = secure_filename(photo.filename)
    photo.save(os.path.join(path, img))
    cmd.execute("insert into login_tb values(null,'" + email + "','" + mobile + "','employee')")
    tid=con.insert_id()
    print(tid)
    cmd.execute("insert into h_add_tb values('"+str(tid)+"','"+str(hid)+"','" + em_type + "','" + name + "','" + place + "','" + dob + "','" + gender + "','" + mobile + "','" + email + "','" + qualification + "','" + experience + "','" + img + "')")
    con.commit()
    return jsonify({'task': "success"})

@app.route('/tl_view_admin_work',methods=['POST','GET'])

def tl_view_admin_work():
    try:
        hid=request.args.get('lid')
        cmd.execute("SELECT * FROM a_assign_work_tb WHERE hid='"+str(hid)+"'")
        row_headers = [x[0] for x in cmd.description]
        emd = cmd.fetchall()
        if emd is not None:
            json_data = []
            for result in emd:
                json_data.append(dict(zip(row_headers, result)))
                print(json_data)
            return jsonify(json_data)
        else:
            return jsonify({"res": "no"})
    except Exception as e:
        print(str(e))
        return jsonify({'result': "na"})



@app.route('/tl_assign_work_employee',methods=['POST','GET'])
def tl_assign_work_employee():
    em_name= request.args.get('')
    time=request.args.get('employee')
    date=request.args.get('employee')
    work = request.files['file']
    img = secure_filename(work.filename)
    work.save(os.path.join(path1, img))
    cmd.execute("insert into h_assign_work_tb values(null,'" +em_name+ "','" +img   + "','" + time + "','" + date + "',' pending ',' pending ')")
    con.commit()
    return jsonify({'task': "success"})



#@app.route('/tl_view_assigned_work',methods=['POST','GET'])
#def tl_view_assigned_work():


#@app.route('/tl_v_report',methods=['POST','GET'])
#def tl_v_report():
    #cmd.execute("SELECT`h_add_tb`.name,`h_assign_work_tb`.`work` ,`report_send_employee`.`date`,`report`,`rid` FROM `h_add_tb` JOIN `h_assign_work_tb` ON `h_add_tb`.eid=`h_assign_work_tb`.eid JOIN `report_send_employee` ON `report_send_employee`.eid=`h_add_tb`.`eid` AND `h_assign_work_tb`.`wr_id`=`report_send_employee`.`wr_id`  ")


#@app.route('/tl_view_report1',methods=['POST','GET'])
#def tl_view_report1():



#@app.route('/tl_view_pc_details',methods=['POST','GET'])
#def tl_view_pc_details():


@app.route('/tl_pc_reg',methods=['POST','GET'])
def tl_pc_reg():
    name=request.args.get('name')
    system_num=request.args.get('system_num')
    mac_ad=request.args.get('mac_ad')
    cmd.execute("insert into h_pc_reg_tb values(null,'" + name + "','" + system_num + "','" + mac_ad + "')")
    con.commit()
    return jsonify({'task': "success"})




if __name__ == "__main__":
    app.run(host="192.168.43.18", port=5000)