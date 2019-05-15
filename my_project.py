import smtplib
from email.mime.text import MIMEText

from flask import *
import MySQLdb
import os
from werkzeug.utils import secure_filename
app=Flask(__name__)
app.static_folder="static"
app.secret_key="new"
path="F:\\project\\new\\Third_Eye\\static\\image"
path1="F:\\project\\new\\Third_Eye\\static\\file"
con=MySQLdb.connect(host='localhost',user='root',passwd='',port=3306,db='project')
cmd=con.cursor()
@app.route('/')
def home():
    return render_template('login.html')
@app.route('/login')
def login():
    return render_template('Login.html')
@app.route('/log',methods=['POST','GET'])
def log():
    username=request.form['textfield']
    password=request.form['textfield2']

    cmd.execute("select * from login_tb where username='"+username+"' and password='"+password+"'")
    s=cmd.fetchone()
    if s is None:
        return '''<script>alert('invalid');window.location="/"</script>'''
    else:
        return '''<script>alert("successfully login");window.location="/admin_home"</script>'''
@app.route('/admin_home')
def admin_home():
    return render_template('A_home.html')
@app.route('/reg')
def reg():
    return render_template('A_add_head.html')
@app.route('/view')
def view():
    cmd.execute("select * from a_add_tb")
    s = cmd.fetchall()
    return render_template('A_view_head.html',val=s)
@app.route('/register',methods=['POST','GET'])
def register():
    type=request.form['select']
    name=request.form['textfield']
    place=request.form['textfield7']
    dob=request.form['textfield6']
    gender=request.form['radiobutton']
    mobile=request.form['textfield2']
    email=request.form['textfield3']
    qualification=request.form['textfield4']
    experience=request.form['textfield5']
    photo=request.files['file']
    img=secure_filename(photo.filename)
    photo.save(os.path.join(path,img))




    try:

        gmail = smtplib.SMTP('smtp.gmail.com', 587)

        gmail.ehlo()

        gmail.starttls()

        gmail.login('project.thirdeye19@gmail.com', 'thirdeye123')

    except Exception as e:

        print("Couldn't setup email!!", str(e))

    msg = MIMEText(" \n Your username is "+email+" and  password is '" +str(mobile) + "'...!!!\n ")

    msg['Subject'] = 'Password'

    msg['To'] = email

    msg['From'] = 'project.thirdeye19@gmail.com'

    try:
        gmail.send_message(msg)

    except Exception as e:
        print("COULDN'T SEND EMAIL", str(e))
    cmd.execute("insert into login_tb values(null,'"+email+"','"+mobile+"','"+type+"')")
    idd=con.insert_id()
    cmd.execute("insert into a_add_tb values('"+str(idd)+"','" + type + "','" +name + "','" + place + "','" + dob + "','" + gender + "','" + mobile + "','"+email+"','"+    qualification+"','"+experience+"','"+ img+"')")
    con.commit()
    return '''<script>alert("successfully registerd");window.location="/admin_home"</script>'''
@app.route('/delete',methods=['POST','GET'])
def delete():
    id=request.args.get('id')
    print(id)
    cmd.execute("delete from a_add_tb where hid=" + str(id) + "")
    con.commit()
    return '''<script>alert("deleted");window.location="/view"</script>'''
@app.route('/update')
def update():
    id=request.args.get('id')
    session['idd']=id
    # session['dd']=id
    cmd.execute("select * from a_add_tb where hid="+ str(id) +"")
    s = cmd.fetchone()
    print(s)
    return render_template('A_update_head.html', val=s)
@app.route('/update1')
def update1():

    id= session['idd']
    cmd.execute("select * from a_add_tb where hid="+ str(id) +"")
    s = cmd.fetchone()
    print(s)
    return render_template('A_update_head.html', val=s)
@app.route('/updated',methods=['POST','GET'])
def updated():
    Id=session['idd']
    print(Id)
    type = request.form['select']
    name = request.form['textfield']
    place = request.form['textfield2']
    dob = request.form['textfield22']
    gender = request.form['radiobutton']
    mobile = request.form['textfield3']
    email = request.form['textfield4']
    qualification = request.form['textfield5']
    experience = request.form['textfield6']
    cmd.execute("update a_add_tb set type='"+type+"',name='" + name + "',place='" + place + "',dob='" + dob + "',gender='" + gender + "',mobile='" + mobile + "',email='" + email + "',qualification='" +qualification  + "',experience='" +experience  + "' where hid='" + str(Id) + "'")
    con.commit()
    return '''<script>alert("successfully updated");window.location="/view"</script>'''
@app.route('/change_photo')
def change_photo():
    id=request.args.get('id')
    session['id']=id
    return render_template('change_pic.html')
@app.route('/change_img',methods=['POST','GET'])
def change_img():
    id = session['id']
    print(id)
    photo = request.files['file']
    img = secure_filename(photo.filename)
    photo.save(os.path.join(path, img))
    cmd.execute("update a_add_tb set photo='"+img+"' where hid='" + str(id) + "' ")
    con.commit()
    return '''<script>alert("success");window.location="/update1"</script>'''


@app.route('/work')
def work():
    return render_template('A_assign_work.html')

@app.route('/index',methods=['POST','GET'])
def index():
    type=request.form['brand']
    cmd.execute("SELECT `hid`,`name` FROM `a_add_tb` WHERE `type`='"+type+"'")
    data=cmd.fetchall()

    d=[]
    d=['0','Select']
    for i in data:
        d.append(i[0])
        d.append(i[1])

    resp = make_response(jsonify(d))
    resp.status_code = 200
    resp.headers['Access-Control-Allow-Origin'] = '*'
    return resp



@app.route('/assign_work',methods=['POST','GET'])
def assign_work():
    type=request.form['select']
    head=request.form['select2']
    title=request.form['textfield']
    work=request.files['file']
    time=request.form['textfield3']
    date=request.form['textfield2']
    img = secure_filename(work.filename)
    work.save(os.path.join(path1, img))
    cmd.execute("insert into a_assign_work_tb values(null,'" +type + "','" + head + "','" + title + "','" + img + "','" + time + "','" + date + "')")
    con.commit()
    return '''<script>alert("success");window.location="/admin_home"</script>'''
@app.route('/view_assigned_work')
def view_assigned_work():
    cmd.execute("select * from a_assign_work_tb")
    s = cmd.fetchall()
    return render_template('A_view_assigned_work.html',val=s)
@app.route('/delete_work',methods=['POST','GET'])
def delete_work():
    id=request.args.get('id')
    print(id)
    cmd.execute("delete from a_assign_work_tb where wid=" + str(id) + "")
    con.commit()
    return '''<script>alert("deleted");window.location="/view_assigned_work"</script>'''


@app.route('/report_view1',methods=['get','post'])
def report_view1():
    type=request.form['select']
    if type=='TL':
        cmd.execute("SELECT`a_add_tb`.name,`h_assign_work_tb`.`work`,`send_report_tb`.`date`,`report`,`aid` FROM `a_add_tb` JOIN `h_assign_work_tb` ON `a_add_tb`.hid=`h_assign_work_tb`.hid JOIN `send_report_tb` ON `send_report_tb`.hid=`a_add_tb`.`hid` AND `h_assign_work_tb`.`wr_id`=`send_report_tb`.`wr_id` WHERE a_add_tb.type='TL'")
        s = cmd.fetchall()
        return render_template('A_view_report.html', val=s)
    else:
        cmd.execute("SELECT`a_add_tb`.name,`mh_assign_work`.`work`,`send_report_tb`.`date`,`report`,`aid` FROM `a_add_tb` JOIN `mh_assign_work` ON `a_add_tb`.hid=`mh_assign_work`.hid JOIN `send_report_tb` ON `send_report_tb`.hid=`a_add_tb`.`hid` AND `mh_assign_work`.`work_id`=`send_report_tb`.`wr_id` WHERE a_add_tb.type='MH'")
        s = cmd.fetchall()
        return render_template('A_view_report.html', val=s)

@app.route('/viewr')
def viewr():
    return render_template('A_view_report.html')
@app.route('/delete_report',methods=['POST','GET'])
def delete_report():
    id=request.args.get('id')
    print(id)
    cmd.execute("delete from send_report_tb where aid='" + str(id) + "'")
    con.commit()
    return '''<script>alert("deleted");window.location="/viewr"</script>'''
@app.route('/view_a',methods=['POST','GET'])
def view_a():
    return render_template('A_view_attendance.html')
@app.route('/view_attendance',methods=['POST','GET'])
def view_attendance():
    date = request.form['textfield']

    cmd.execute("SELECT `h_add_tb`.`name`,`a_view_attendance_tb`.`at_id`,`time`,`attendance` FROM `h_add_tb` JOIN `a_view_attendance_tb` ON `h_add_tb`.eid=`a_view_attendance_tb`.eid where `a_view_attendance_tb`.date='"+date+"' ")
    s = cmd.fetchall()
    return render_template('A_view_attendance.html', val=s)
@app.route('/delete_att',methods=['POST','GET'])
def delete_att():
    id=request.args.get('id')
    print(id)
    cmd.execute("delete from a_view_attendance_tb where at_id='" + str(id) + "'")
    con.commit()
    return '''<script>alert("deleted");window.location="/view_a"</script>'''


if(__name__=='__main__'):
    app.run(debug=True)
