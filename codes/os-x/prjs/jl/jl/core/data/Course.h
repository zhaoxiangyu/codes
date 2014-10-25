#ifndef COURSE_H
#define COURSE_H


class Course
{
	public:
		Course();
		virtual ~Course();

		int getCourseNo();
		void setCourseNo(int courseNo);
	protected:
	private:
		int courseNo;
};

#endif // COURSE_H
