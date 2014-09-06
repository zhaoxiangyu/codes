#ifndef COURSE_H
#define COURSE_H


class Course
{
	public:
		Course();
		virtual ~Course();

		int getCourseNo();
		void setCourseNo(int courseNo);
		int getStatus();
		void setStatus(int status);
		int getZipSize();
		void setZipSize(int zipSize);

		static const int STATUS_DOWNLOADED;
		static const int STATUS_NOTEXIST_NOINFO;
		static const int STATUS_NOTEXIST;
	protected:
	private:
		int courseNo;
		int status;
		int zipSize;
};

#endif // COURSE_H
