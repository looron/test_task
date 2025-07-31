const table = document.getElementById('table'); 
const info = document.createElement('p');
const html = document.querySelector('html');

const contentDiv = document.getElementById("content");

document.body.appendChild(info);
document.body.appendChild(table);

// функция изменения отображения дедлайна
function day_to_deadline(date_now,date_task)
{
  if(date_now < date_task)
    {
      return Math.round((date_now.getTime()-date_task.getTime())/86400000) + " дней до истечения срока"
    }

  else
    {
        return "просрочено на "+Math.round((date_now.getTime()-date_task.getTime())/86400000)+" дней";
    }
}

// функция добовления новой строки к таблице
function createRow(id,taskName, taskText, deadline,taskComplite,  )
{
    const row = document.createElement("tr");  
    const nameColumn = document.createElement("td");  
    const textColumn = document.createElement("td");  
    const deadlineColumn = document.createElement("td");  
    const idColumn = document.createElement("td");  
    const compliteColumn = document.createElement("td");  
    
    //тригер нажатия ячейки. меняет статус задачи и цвет строки
    compliteColumn.addEventListener("click",function()
    {
      change_color_row(this,idColumn.textContent,row);
    });
    
    let date_now= new Date();
    let date_task = new Date(deadline);

    idColumn.appendChild(document.createTextNode(id));  
    nameColumn.appendChild(document.createTextNode(taskName));  
    textColumn.appendChild(document.createTextNode(taskText));  
    deadlineColumn.appendChild(document.createTextNode(deadline+"("+day_to_deadline(date_now,date_task)+")"));  
    compliteColumn.appendChild(document.createTextNode(taskComplite));  

    row.appendChild(idColumn); 
    row.appendChild(nameColumn);  
    row.appendChild(textColumn);  
    row.appendChild(deadlineColumn); 
    row.appendChild(compliteColumn);  
    return row;
}



 const x =new XMLHttpRequest();
    x.onload=()=>
    {
      if (x.status ==200 )
        {
          const json=x.response;
          console.log(json);
          const tasks =   json.tasks;
          for (let i=0;i < tasks.length; i++)
            {
              const task = tasks[i];
              const row = createRow(task.id_task,task.task_name,task.task_text,task.deadline, task.complite);
              table.appendChild(row)
              colored(row,task.complite);
            }
        }
    };

  x.open('get','http://localhost:8090/api/tasks',true);
  x.responseType="json";
  x.setRequestHeader("Accept", "application/json");
  x.send();

 document.addEventListener('load',all_colored()); //тригер на закрашивание строк таблицы по окончании ее загрузки

 // функция для перебора строк и закрашивания
 function all_colored()
 {
  const rows = document.getElementsByTagName('tr');
  for (let i=0;i<rows.length;i++)
    {
      const row=rows[i];
      const com=row[4];
      colored(row,com);
    }
 }

// функция закрашивания строки
function colored(row,com)
{
    if (com == 0 )
    {
      row.style.backgroundColor='red';
    }

    if (com == 1)
        {
            row.style.backgroundColor='green';
        }
}
