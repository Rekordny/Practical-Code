import React from "react";

const WEEK_LETTERS = ['S', 'M', 'T', 'W', 'T', 'F', 'S']
const LINES=[1,2,3,4,5,6]
const MONTH_DAYS = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]

export default class Calendar extends React.Component{
    state = {
        month: 0,
        year: 0,
        currentDate: new Date()
    }

    setCurrentYearMonth(date) {
        var month = Calendar.getMonth(date)
        var year = Calendar.getFullYear(date)
        this.setState({
          month,
          year
        })
    }

    static getCurrentMonthDays(month) {
        return MONTH_DAYS[month]
    }
    
    static getMonth(date){
        return date.getMonth()
    }
    
    static getFullYear(date){
        return date.getFullYear()
    }

    static getWeeksByFirstDay(year, month) {
        var date = Calendar.getDateByYearMonth(year, month)
        return date.getDay()
      }


    static getDayText(line, weekIndex, weekDay, monthDays) {
        var number = line * 7 + weekIndex - weekDay + 1
        if ( number <= 0 || number > monthDays ) {
          return <span>&nbsp;</span>
        }
    
        return number
    }

    render(){
        return(
            <div>
                <table cellPadding={0} cellSpacing={0} className='table'>
                    <thead>
                        <tr>{
                            WEEK_LETTERS.map((week,key)=>{
                                return <td key={key}>{week}</td>
                            })
                        }</tr>
                    </thead>
                    <tbody>
                        {
                            LINES.map((l, key) => {
                            return <tr key={key}>{
                                WEEK_NAMES.map((week, index) => {
                                return <td key={index}>{index}</td>
                                })
                            }</tr>
                            })
                        }
                    </tbody>
                </table>
            </div>
        )
    }
}