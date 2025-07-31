
let form =
document.getElementsByTagName('form');
console.log(form[0]);

//триггер-перехватчик на удаление записи при нажатии кнопки формы
form[1].addEventListener('submit',function ()
{
const formData= new FormData(this);
let id = formData.get("id");


  if (confirm("вы точно хотите удалить эту задачу?"))
  {
  x.open('delete','http://localhost:8090/api/tasks/'+id,true);
  console.log('http://localhost:8090/api/tasks/'+id);
  x.responseType="text";
  x.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
  x.send();
  
  }
});

// тригер-перехватчик на добавление новой записи + проверяет корректность введенной даты
form[0].addEventListener('submit',function()
{
  const formData= new FormData(this);
let date = formData.get("deadline");
const regex =/^\d{4}-\d{2}-\d{2}$/; 
let date_now=new Date();
let date_task= new Date(date)


if (formData.get("task_name")!="" && formData.get("task_text")!="" )
{

if (regex.test(date) && date_task>date_now)
  {
    let url= new URL('http://localhost:8090/api/tasks');
    url.searchParams.set("task_name",formData.get("task_name"));
    url.searchParams.set("task_text",formData.get("task_text"));
    url.searchParams.set("deadline",date);
    
    x.open('post',url,true);
    x.responseType="text";
    x.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");

    x.send();
  }

else {alert("ведена дата неправильного формата или прошедшая дата. формат даты должен быть гггг-ММ-дд");}

}

else {alert("заполните название задачи и её содержание");}

});

// функция для изменения статуса задачи 
function change_color_and_status_row(e,id,row)
{
if (e.textContent == "false")
    {
    row.style.backgroundColor='green';
    e.textContent="true";
    }

else
  {
    row.style.backgroundColor='red';
    e.textContent = "false";
  }
  x.open('put','http://localhost:8090/api/tasks/'+id+'/toggle',true);
  x.responseType="text";
  x.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
  x.send();
    
}